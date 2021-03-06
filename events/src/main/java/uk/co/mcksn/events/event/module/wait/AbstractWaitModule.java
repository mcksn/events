package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.eventstream.EventStream;

public abstract class AbstractWaitModule<Waitable> {

	protected Waitable waitable;

	private Long timeout = 20000L;

	public AbstractWaitModule(Waitable event) {
		super();
		this.waitable = event;
	}

	/**
	 * Typically when an event occurs, the framework will wake up all the
	 * threads that called wait( ) on the event. The highest priority thread
	 * will run first. Initial use for this was so that the
	 * {@link EventStream#when(uk.co.mcksn.events.blackbox.event.multi.ComplexEvent) }
	 * and {@link EventStream#when(uk.co.mcksn.events.blackbox.event.multi.EventTree)}
	 * plots could suspend the story until the event occurred.
	 * </p>
	 * TODO Oracle suggests 'spurious wakeups' may occur so a loop should be
	 * used to guard against the thread waking up unintentionally.
	 * 
	 * <pre>
	 * synchronized (obj) {
	     while (<condition does not hold>)
	         obj.wait(timeout);
	     ... // Perform action appropriate to condition
	 }
	 * </pre>
	 */
	public void doNotify() {
		synchronized (waitable) {
			waitable.notify();

		}

	}

	/**
	 * Typically when an event occurs, the framework will wake up all the
	 * threads that called wait( ) on the event. The highest priority thread
	 * will run first. Initial use for this was so that the
	 * {@link EventStream#when(uk.co.mcksn.events.blackbox.event.multi.ComplexEvent) }
	 * and {@link EventStream#when(uk.co.mcksn.events.blackbox.event.multi.EventTree)}
	 * plots could suspend the story until the event occurred.
	 * </p>
	 * TODO Oracle suggests 'spurious wakeups' may occur so a loop should be
	 * used to guard against the thread waking up unintentionally.
	 * 
	 * <pre>
	 * synchronized (obj) {
	     while (<condition does not hold>)
	         obj.wait(timeout);
	     ... // Perform action appropriate to condition
	 }
	 * </pre>
	 */
	public void doWait() {
		synchronized (waitable) {
			try {
				waitable.wait(timeout);
			} catch (InterruptedException e) {
				System.err.println(e);
				// TODO Log. Research how useful notifier pattern is. Would make
				// it difficult to debug
			}
		}
	}

	/**
	 * FEATURE end user
	 * </p>
	 * Set number of milliseconds the framework should wait for the event to
	 * occur before it stops waiting. This is used for the
	 * {@link EventStream#when(uk.co.mcksn.events.blackbox.event.multi.ComplexEvent) }
	 * and {@link EventStream#when(uk.co.mcksn.events.blackbox.event.multi.EventTree)}
	 * plots.
	 * </p>
	 * TODO This is associated with wait plots. It is not available for
	 * {@link EventStream#expect(Event)}. Should it be? Look into whether this a
	 * viable scenario?
	 * 
	 * TODO
	 * </p>
	 * 
	 * @param timeout
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public Long getTimeout() {
		return timeout;
	}

	public abstract void registerForWait(RegisterForWaitStrategyFactory strategyFactory);


}
