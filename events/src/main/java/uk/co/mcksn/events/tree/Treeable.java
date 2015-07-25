package uk.co.mcksn.events.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.action.ActionModule;
import uk.co.mcksn.events.event.module.result.ResultModule;
import uk.co.mcksn.events.event.module.tree.AbstractTreeModule;
import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;

@SuppressWarnings("rawtypes")
public interface Treeable<A extends ActionModule, R extends ResultModule, V extends VerificationPolicyModule>
		extends Event<A,R,V> {

	AbstractTreeModule<? extends Treeable> getTreeModule();

}