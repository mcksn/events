package uk.co.mcksn.events.stack;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.occured.AbstractOccuredModule;
import uk.co.mcksn.events.tree.TreeTraverser;
import uk.co.mcksn.events.tree.RecursiveTreeTraverserImpl;
import uk.co.mcksn.events.type.Waitable;

public abstract class AbstractUpdateEventsStackWork<E extends Event> implements UpdateEventsStackWork {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateEventsStackWork.class);
	private TreeTraverser treeTraverser = new RecursiveTreeTraverserImpl();

	public AbstractUpdateEventsStackWork() {
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

		if (matchEvent instanceof Waitable) {
			Waitable rootMatchedEvent = treeTraverser.getRootEventTreeable((Waitable) matchEvent);
			EventState rootMatchedEventState = ((Event) rootMatchedEvent).getEventOccurredModule().getState();
			if (rootMatchedEventState.equals(EventState.OCCURRED)) {
				doNotifyOnEvent(rootMatchedEvent);
			}
		}

	}

	private void doNotifyOnEvent(Waitable waitable) {
		if (waitable != null) {
			waitable.getWaitModule().doNotify();
		}
	}

	protected abstract E matchWorkToEvent(Collection<Event> events);

	protected abstract void updateResultModule(E matchedEvent);

}
