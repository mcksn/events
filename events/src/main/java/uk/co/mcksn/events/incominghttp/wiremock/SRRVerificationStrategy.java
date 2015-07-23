package uk.co.mcksn.events.incominghttp.wiremock;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.type.Verifyable;

public class SRRVerificationStrategy implements VerificationStrategy {

	@Override
	public VerificationOutcome calculateVerificationOutcome(Verifyable verifyable) {
		if (!verifyable.getEventOccurredModule().getState().equals(EventState.OCCURRED))
			return VerificationOutcome.UNKOWN;

		ServerReceivesRequestEvent srrEvent = cast(verifyable);

		boolean success = srrEvent.getVerificationPolicyModule().getRequestPattern()
				.isMatchedBy(srrEvent.getResultModule().getLoggedRequest());

		if (success)
			return VerificationOutcome.SUCCESS;
		else
			return VerificationOutcome.FAULURE;
	}

	private static ServerReceivesRequestEvent cast(Verifyable verifyable) {
		if (verifyable instanceof ServerReceivesRequestEvent) {
			return (ServerReceivesRequestEvent) verifyable;
		}
		throw new RuntimeException("Can not cast object to " + ServerReceivesRequestEvent.class.getName());
	}
}
