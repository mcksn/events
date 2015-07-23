package uk.co.mcksn.events.eventstream;

import java.lang.reflect.ParameterizedType;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;
import uk.co.mcksn.events.tree.Treeable;
import uk.co.mcksn.events.type.Expectable;
import uk.co.mcksn.events.type.Simulateable;
import uk.co.mcksn.events.type.Whenable;

public abstract class AbstractEventHandler<E extends Event> {

	private EventStream eventStream = null;

	@SuppressWarnings("unchecked")
	public Class<E> getAssociatedEventType() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected abstract <SimulatePlotableEvent extends Event & Simulateable> void simulate(
			SimulatePlotableEvent simulatePlotableEvent);

	public abstract VerificationStrategy getVerificationStrategy();

	public abstract RegisterForWaitStrategy getRegisterForWaitStrategy();

	protected void setEventStream(EventStream eventStream) {
		this.eventStream = eventStream;
	}

	protected ThreadSafeEventStackWorker getEventQueueWorker() {
		return eventStream.getEventQueueWorker();
	}

}
