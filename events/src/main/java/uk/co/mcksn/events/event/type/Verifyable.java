package uk.co.mcksn.events.event.type;

import uk.co.mcksn.events.event.module.occured.AbstractOccuredModule;
import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;

@SuppressWarnings("rawtypes")
public interface Verifyable<VPolicy extends VerificationPolicyModule>  extends Nameable {


	AbstractOccuredModule<? extends Verifyable> getOccurredModule();
	
	VPolicy getVerificationPolicyModule();
	
	void setVerificationPolicyModule(VPolicy verificationPolicyModules);

}
