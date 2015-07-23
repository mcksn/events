package uk.co.mcksn.events.stack;

import java.util.Collection;

import uk.co.mcksn.events.event.Event;

public interface UpdateEventsStackWork extends Work {

	void doWork(Collection<Event> eventQueue);
}
