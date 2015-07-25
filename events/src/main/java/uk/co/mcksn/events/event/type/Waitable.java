package uk.co.mcksn.events.event.type;

import uk.co.mcksn.events.event.module.action.ActionModule;
import uk.co.mcksn.events.event.module.result.ResultModule;
import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;
import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public interface Waitable<A extends ActionModule, R extends ResultModule, V extends VerificationPolicyModule> extends Treeable<A,R,V>  {

	AbstractWaitModule<? extends Waitable> getWaitModule();

}
