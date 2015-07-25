package uk.co.mcksn.events.event;

import uk.co.mcksn.events.event.module.action.ActionModule;
import uk.co.mcksn.events.event.module.result.ResultModule;
import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;
import uk.co.mcksn.events.event.type.Nameable;
import uk.co.mcksn.events.event.type.Verifyable;

/**
 * An Event is model of what the end user thinks will happen within the test
 * environment. The framework can then use this description to gather data on
 * the occurrence of an event.
 * 
 * The end user sets the {@link ActionModule} and the
 * {@link VerificationPolicyModule}. The framework will use the action to decide
 * on whether the event occurred in the test environment and record the results
 * in the {@link ResultModule}. When the end user asks the framework to verify
 * it can use the {@link VerificationPolicyModule} set initially.
 * 
 * @author mackson
 * @param <V>
 *
 */
public interface Event<A extends ActionModule, R extends ResultModule, V extends VerificationPolicyModule>
		extends Verifyable<V> {

	A getActionModule();

	void setActionModule(A action);

	R getResultModule();

	void setResultModule(R result);

	V getVerificationPolicyModule();

	void setVerificationPolicyModule(V verificationPolicy);

}
