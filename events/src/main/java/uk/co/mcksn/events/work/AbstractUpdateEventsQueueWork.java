package uk.co.mcksn.events.work;

import java.util.Collection;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.multi.traverser.EventTreeTraverser;
import uk.co.mcksn.events.event.multi.traverser.RecursiveEventTraverserImpl;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.plot.WaitPlotable;
import uk.co.mcksn.events.plot.WhenPlotable;

public abstract class AbstractUpdateEventsQueueWork implements UpdateEventsQueueWork {

	private ThreadSafeEventQueueWorker threadSafeEventQueueWorker = null;
	private EventTreeTraverser eventTreeTraverser = new RecursiveEventTraverserImpl();

	public AbstractUpdateEventsQueueWork(ThreadSafeEventQueueWorker threadSafeEventQueueWorker) {
		super();
		this.threadSafeEventQueueWorker = threadSafeEventQueueWorker;
	}

	public void doWork(Collection<Event> events) {

		Event matchEvent = matchWorkToEvent(events);
		if (matchEvent == null) {
			System.out.println("WARN: Real world event could not be matched to registered event");
			// TODO Use logger instead
		}

		if (matchEvent instanceof EventTreeable) {
			ComplexEvent complexEvent = eventTreeTraverser.getComplexEventOfRootTree((EventTreeable) matchEvent);
			EventState complextEventState = complexEvent.getUpdatedState();
			if (complextEventState.equals(EventState.OCCURRED)) {
				doNotifyOnEvent(complexEvent);
			}
		}

		if (matchEvent instanceof WaitPlotable) {
			doNotifyOnEvent((WaitPlotable) matchEvent);
		}

	}

	private void doNotifyOnEvent(WaitPlotable event) {
		if (event != null) {
			event.doNotify();
		}
	}

	protected abstract Event matchWorkToEvent(Collection<Event> events);

}
