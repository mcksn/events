package uk.co.mcksn.events.event;

import uk.co.mcksn.events.event.action.Action;
import uk.co.mcksn.events.event.module.occured.AbstractEventOccuredModule;
import uk.co.mcksn.events.event.result.Result;
import uk.co.mcksn.events.event.verificationpolicy.VerificationPolicy;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

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
public interface Event<A extends Action, R extends Result, V extends VerificationPolicy> extends VerifyPlotable {

	String getName();

	void setName(String name);

	A getAction();

	void setAction(A action);

	R getResult();

	void setResult(R result);

	V getVerificationPolicy();

	void setVerificationPolicy(V verificationPolicy);

	EventState getState();

	void setState(EventState flag);
	
	AbstractEventOccuredModule getEventOccurredModule();

	VerificationOutcome getVerificationOutcome();

	void setVerificationOutcome(VerificationOutcome verificationOutcome);
}
