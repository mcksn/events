package uk.co.mcksn.events.stack;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;

import uk.co.mcksn.events.event.Event;

@SuppressWarnings("rawtypes")
public class ThreadSafeEventStackWorker {

	private Queue<Event> eventStack = new LinkedList<Event>();

	public void doWork(UpdateEventsStackWork work) {
		synchronized (this) {
			work.doWork(eventStack);
		}
	}

	public List<Event> copy() {
		synchronized (this) {
			return ImmutableList.copyOf(eventStack);
		}
	}

	public void add(Event event) {
		synchronized (this) {
			eventStack.add(event);
		}
	}

}
