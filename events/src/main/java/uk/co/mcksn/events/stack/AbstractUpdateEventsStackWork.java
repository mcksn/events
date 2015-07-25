package uk.co.mcksn.events.stack;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.tree.RecursiveTreeTraverserImpl;
import uk.co.mcksn.events.tree.TreeTraverser;
import uk.co.mcksn.events.type.Waitable;

@SuppressWarnings("rawtypes")
public abstract class AbstractUpdateEventsStackWork<E extends Event> implements UpdateEventsStackWork {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateEventsStackWork.class);
	private TreeTraverser treeTraverser = new RecursiveTreeTraverserImpl();

	public AbstractUpdateEventsStackWork() {
		super();
	}

	public void doWork(Collection<Event> events) {

		E matchEvent = matchWorkToEvent(events);

		if (matchEvent == null) {
			System.err.println("Event ocurred but not on the Stream.");
			return;
		}
		matchEvent.getOccurredModule().setState(EventState.OCCURRED);
		LOGGER.info("Eyewitnesses say the following event occured: " + matchEvent.getName());
		
		updateResultModule(matchEvent);

		if (matchEvent instanceof Waitable) {
			Waitable rootMatchedEvent = treeTraverser.getRootEventTreeable((Waitable) matchEvent);
			EventState rootMatchedEventState = ((Event) rootMatchedEvent).getOccurredModule().getState();
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
