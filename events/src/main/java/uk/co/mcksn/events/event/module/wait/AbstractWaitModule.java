package uk.co.mcksn.events.event.module.wait;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.event.strategy.VerificationStrategy;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.plot.WaitPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;
import uk.co.mcksn.events.story.Story;

public abstract class AbstractWaitModule<W extends WaitPlotable> {

	protected W waitPlotable;
	private W parent;
	protected Collection<WaitPlotable> leaves = new ArrayList<WaitPlotable>();

	private Long timeout = 20000L;

	public AbstractWaitModule(W event) {
		super();
		this.waitPlotable = event;
	}

	/**
	 * Typically when an event occurs, the framework will wake up all the
	 * threads that called wait( ) on the event. The highest priority thread
	 * will run first. Initial use for this was so that the
	 * {@link Story#when(uk.co.mcksn.events.blackbox.event.multi.ComplexEvent) }
	 * and {@link Story#when(uk.co.mcksn.events.blackbox.event.multi.EventTree)}
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
		synchronized (this) {
			waitPlotable.notify();

		}

	}

	/**
	 * Typically when an event occurs, the framework will wake up all the
	 * threads that called wait( ) on the event. The highest priority thread
	 * will run first. Initial use for this was so that the
	 * {@link Story#when(uk.co.mcksn.events.blackbox.event.multi.ComplexEvent) }
	 * and {@link Story#when(uk.co.mcksn.events.blackbox.event.multi.EventTree)}
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
		synchronized (this) {
			try {
				this.wait(timeout);
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
	 * {@link Story#when(uk.co.mcksn.events.blackbox.event.multi.ComplexEvent) }
	 * and {@link Story#when(uk.co.mcksn.events.blackbox.event.multi.EventTree)}
	 * plots.
	 * </p>
	 * TODO This is associated with wait plots. It is not available for
	 * {@link Story#expect(Event)}. Should it be? Look into whether this a
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

	public W getParent() {
		return this.parent;
	}

	public void setParent(W parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(W rootParent) {
		setParent(rootParent);
		for (WaitPlotable waitPlotable : leaves) {
			waitPlotable.getWaitModule().setParent(this.waitPlotable);
		}
	}
	
	public abstract void registerForWait(RegisterForWaitStrategyFactory strategyFactory);


}
