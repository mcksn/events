package uk.co.mcksn.events.work;

import java.util.Collection;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventTree;
import uk.co.mcksn.events.event.multi.traverser.EventTreeTraverser;
import uk.co.mcksn.events.event.multi.traverser.RecursiveEventTraverserImpl;

public abstract class AbstractUpdateEventsQueueWork implements UpdateEventsQueueWork {

	private ThreadSafeEventQueueWorker threadSafeEventQueueWorker = null;
	private EventTreeTraverser eventTreeTraverser = new RecursiveEventTraverserImpl();

	public AbstractUpdateEventsQueueWork(ThreadSafeEventQueueWorker threadSafeEventQueueWorker) {
		super();
		this.threadSafeEventQueueWorker = threadSafeEventQueueWorker;
	}

	public void doWork(Collection<Event> events) {

		Event matchEvent = matchWorkToEvent(events);

		doNotifyOnEvent(matchEvent);

		if (matchEvent instanceof EventTree) {
			ComplexEvent complexEvent = eventTreeTraverser.getComplexEventOfRootTree((EventTree) matchEvent);
			EventState complextEventState = complexEvent.getUpdatedState();
			if (complextEventState.equals(EventState.OCCURRED)) {

				doNotifyOnEvent(complexEvent);
			}
		}

	}

	private void doNotifyOnEvent(Event event) {
		if (event != null) {
			event.doNotify();
		}
	}

	protected abstract Event matchWorkToEvent(Collection<Event> events);

}
