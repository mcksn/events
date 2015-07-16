package uk.co.mcksn.events.work;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.module.occured.AbstractEventOccuredModule;
import uk.co.mcksn.events.event.multi.traverser.EventTreeTraverser;
import uk.co.mcksn.events.event.multi.traverser.RecursiveEventTraverserImpl;
import uk.co.mcksn.events.plot.WaitPlotable;

public abstract class AbstractUpdateEventsQueueWork<E extends Event> implements UpdateEventsQueueWork {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateEventsQueueWork.class);
	private EventTreeTraverser eventTreeTraverser = new RecursiveEventTraverserImpl();

	public AbstractUpdateEventsQueueWork() {
		super();
	}

	public void doWork(Collection<Event> events) {

		E matchEvent = matchWorkToEvent(events);

		if (matchEvent == null) {
			System.err.println("Event was not found during matching");
			return;
		}
		matchEvent.getEventOccurredModule().setState(EventState.OCCURRED);
		LOGGER.info("Eyewitnesses say the following event occured: " + matchEvent.getName());

		if (matchEvent == null) {
			LOGGER.info("WARN: Real world event could not be matched to registered event");
		}

		updateResultModule(matchEvent);

		if (matchEvent instanceof WaitPlotable) {
			WaitPlotable rootMatchedEvent = eventTreeTraverser.getRootEventTreeable((WaitPlotable) matchEvent);
			EventState rootMatchedEventState = ((Event) rootMatchedEvent).getEventOccurredModule().getState();
			if (rootMatchedEventState.equals(EventState.OCCURRED)) {
				doNotifyOnEvent(rootMatchedEvent);
			}
		}

	}

	private void doNotifyOnEvent(WaitPlotable waitPlotable) {
		if (waitPlotable != null) {
			waitPlotable.getWaitModule().doNotify();
		}
	}

	protected abstract E matchWorkToEvent(Collection<Event> events);

	protected abstract void updateResultModule(E matchedEvent);

}
