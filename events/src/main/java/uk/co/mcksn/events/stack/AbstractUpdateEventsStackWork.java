package uk.co.mcksn.events.stack;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.tree.RecursiveTreeTraverserImpl;
import uk.co.mcksn.events.tree.TreeTraverser;

@SuppressWarnings("rawtypes")
public abstract class AbstractUpdateEventsStackWork<E extends Event> implements UpdateEventsStackWork {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateEventsStackWork.class);
	private TreeTraverser treeTraverser = new RecursiveTreeTraverserImpl();

	public AbstractUpdateEventsStackWork() {
		super();
	}

	public void doWork(Collection<Event> events) {

		E matchedEvent = matchIncidentToEvent(events);
		
		if (matchedEvent == null) {
			System.err.println("Event ocurred but not on the Stream.");
			return;
		}

		updateResultModule(matchedEvent);
		

		if (getState(matchedEvent) == EventState.IN_PROGRESS) {
			return;
		}
		
		matchedEvent.getOccurredModule().setState(EventState.OCCURRED);
		LOGGER.info("Event occured: " + matchedEvent.getName());

		if (matchedEvent instanceof Waitable)
		{
			Waitable rootMatchedEvent = treeTraverser.getRootEventTreeable(Waitable.class, (Waitable) matchedEvent);
			EventState rootMatchedEventState = ((Event) rootMatchedEvent).getOccurredModule().getState();
			if (rootMatchedEventState.equals(EventState.OCCURRED)) {
				doNotifyOnEvent(rootMatchedEvent);
			}
		}

	}

	private void doNotifyOnEvent(Waitable waitable) {
			waitable.getWaitModule().doNotify();
	}

	protected abstract E matchIncidentToEvent(Collection<Event> events);

	protected abstract void updateResultModule(E matchedEvent);

	protected abstract EventState getState(E matchedEvent);

}
