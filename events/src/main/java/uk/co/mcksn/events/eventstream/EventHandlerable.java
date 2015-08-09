package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;

public interface EventHandlerable {

	Class<? extends Event> getEventType();

	VerificationStrategy getVerificationStrategy();

	void setEventStream(EventStream eventStream);
	
	void startWatching();

	ThreadSafeEventStackWorker getEventQueueWorker();
	
	void cleanUp();

}