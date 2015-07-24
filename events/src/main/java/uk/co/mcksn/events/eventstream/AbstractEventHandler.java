package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;

@SuppressWarnings("rawtypes")
public abstract class AbstractEventHandler {

	private EventStream eventStream = null;

	public abstract Class<? extends Event> getEventType();

	public abstract VerificationStrategy getVerificationStrategy();

	protected void setEventStream(EventStream eventStream) {
		this.eventStream = eventStream;
	}

	protected ThreadSafeEventStackWorker getEventQueueWorker() {
		return eventStream.getEventQueueWorker();
	}
	


}
