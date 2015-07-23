package uk.co.mcksn.events.type;

import uk.co.mcksn.events.event.module.occured.AbstractOccuredModule;
import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;

public interface Verifyable<VPolicy extends VerificationPolicyModule>  extends Nameable {

	public AbstractOccuredModule getEventOccurredModule();
	
	public VPolicy getVerificationPolicyModule();
	
	public void setVerificationPolicyModule(VPolicy verificationPolicyModules);

}
