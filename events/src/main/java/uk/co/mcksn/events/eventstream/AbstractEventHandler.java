package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;

@SuppressWarnings("rawtypes")
public abstract class AbstractEventHandler implements EventHandlerable {

	private EventStream eventStream = null;

	@Override
	public abstract Class<? extends Event> getEventType();

	@Override
	public abstract VerificationStrategy getVerificationStrategy();

	protected void setEventStream(EventStream eventStream) {
		this.eventStream = eventStream;
	}

	protected ThreadSafeEventStackWorker getEventQueueWorker() {
		return eventStream.getEventQueueWorker();
	}
	


}
