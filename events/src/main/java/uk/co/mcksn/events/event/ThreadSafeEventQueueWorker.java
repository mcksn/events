package uk.co.mcksn.events.event;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;

import uk.co.mcksn.events.work.UpdateEventsQueueWork;

public class ThreadSafeEventQueueWorker {

	private Queue<Event> eventQueue = new LinkedList<Event>();

	public void doWork(UpdateEventsQueueWork work) {
		synchronized (this) {
			work.doWork(eventQueue);
		}
	}

	public List<Event> copy() {
		synchronized (this) {
			return ImmutableList.copyOf(eventQueue);
		}
	}

	public void add(Event event) {
		synchronized (this) {
			eventQueue.add(event);
		}
	}

}
