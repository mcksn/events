package uk.co.mcksn.events.event.type;

import uk.co.mcksn.events.event.module.action.ActionModule;
import uk.co.mcksn.events.event.module.result.ResultModule;
import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;

public interface Expectable<A extends ActionModule, R extends ResultModule, V extends VerificationPolicyModule>
		extends Waitable<A, R, V> {
}
