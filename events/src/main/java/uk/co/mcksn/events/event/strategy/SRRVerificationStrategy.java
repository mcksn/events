package uk.co.mcksn.events.event.strategy;

import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public class SRRVerificationStrategy implements VerificationStrategy {

	@Override
	public VerificationOutcome calculateVerificationOutcome(VerifyPlotable verifyPlotable) {
		if (!verifyPlotable.getEventOccurredModule().getState().equals(EventState.OCCURRED))
			return VerificationOutcome.UNKOWN;

		ServerReceivesRequestEvent srrEvent = cast(verifyPlotable);

		boolean success = srrEvent.getVerificationPolicyModule().getRequestPattern()
				.isMatchedBy(srrEvent.getResultModule().getLoggedRequest());

		if (success)
			return VerificationOutcome.SUCCESS;
		else
			return VerificationOutcome.FAULURE;
	}

	private static ServerReceivesRequestEvent cast(VerifyPlotable verifyPlotable) {
		if (verifyPlotable instanceof ServerReceivesRequestEvent) {
			return (ServerReceivesRequestEvent) verifyPlotable;
		}
		throw new RuntimeException("Can not cast object to " + ServerReceivesRequestEvent.class.getName());
	}
}
