package uk.co.mcksn.events.plot;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.story.Story;

public interface WaitPlotable{
	

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
