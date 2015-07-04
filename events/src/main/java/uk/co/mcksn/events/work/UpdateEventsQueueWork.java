package uk.co.mcksn.events.work;

import java.util.Collection;

import uk.co.mcksn.events.event.Event;

public interface UpdateEventsQueueWork extends Work {

	void doWork(Collection<Event> eventQueue);
}
