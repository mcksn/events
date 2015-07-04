package uk.co.mcksn.events.event.verificationpolicy;

public interface VerificationPolicy {
	
	public void verifyAndContinueStory(boolean flag);
	
	public boolean verifyAndContinueStory();

}
