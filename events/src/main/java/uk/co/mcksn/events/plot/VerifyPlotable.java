package uk.co.mcksn.events.plot;

import uk.co.mcksn.events.event.module.occured.AbstractEventOccuredModule;
import uk.co.mcksn.events.event.verificationpolicy.VerificationPolicyModule;

public interface VerifyPlotable<VPolicy extends VerificationPolicyModule> extends Nameable {

	public AbstractEventOccuredModule getEventOccurredModule();
	
	public VPolicy getVerificationPolicyModule();
	
	public void setVerificationPolicyModule(VPolicy verificationPolicyModules);

}
