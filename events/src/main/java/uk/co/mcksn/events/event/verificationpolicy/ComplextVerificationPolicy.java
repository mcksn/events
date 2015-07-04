package uk.co.mcksn.events.event.verificationpolicy;

public class ComplextVerificationPolicy  implements VerificationPolicy{

	private boolean verifyAndContinueStory =false;

	public boolean verifyAndContinueStory() {
		return verifyAndContinueStory;
	}

	public void verifyAndContinueStory(boolean verifyAndContinueStory) {
		this.verifyAndContinueStory = verifyAndContinueStory;
	}
	
	


}
