package uk.co.mcksn.events.event;

import uk.co.mcksn.events.event.action.Action;
import uk.co.mcksn.events.event.result.Result;
import uk.co.mcksn.events.event.verificationpolicy.VerificationPolicy;
import uk.co.mcksn.events.story.Story;

/**
 * An Event is model of what the end user thinks will happen within the test
 * environment. The framework can then use this description to gather data on
 * the occurrence of an event.
 * 
 * The end user sets the {@link Action} and the {@link VerificationPolicy}. The
 * framework will use the action to decide on whether the event occured in the
 * test environment and record the results in the {@link Result}. When the end
 * user asks the framework to verify it can use the {@link VerificationPolicy}
 * set initially.
 * 
 * @author mackson
 *
 */
public interface Event<A extends Action, R extends Result, V extends VerificationPolicy> {

	A getAction();

	void setAction(A action);

	R getResult();

	void setResult(R result);

	V getVerificationPolicy();

	void setVerificationPolicy(V verificationPolicy);

	void setState(EventState flag);

	EventState getState();

	String getName();

	void setName(String name);

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
	void doWait();

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
	void doNotify();

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
	void setTimeout(Long timeout);

}
