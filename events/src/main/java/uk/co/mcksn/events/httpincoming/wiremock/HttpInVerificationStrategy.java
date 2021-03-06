package uk.co.mcksn.events.httpincoming.wiremock;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.type.Verifyable;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;

@SuppressWarnings("rawtypes")
public class HttpInVerificationStrategy implements VerificationStrategy {

	public VerificationOutcome calculateVerificationOutcome(Verifyable verifyable) {
		if (!verifyable.getOccurredModule().getState().equals(EventState.OCCURRED))
			return VerificationOutcome.UNKOWN;

		HttpInEvent httpInEvent = cast(verifyable);

			return VerificationOutcome.SUCCESS;
	}

	private static HttpInEvent cast(Verifyable verifyable) {
		if (verifyable instanceof HttpInEvent) {
			return (HttpInEvent) verifyable;
		}
		throw new RuntimeException("Can not cast object to " + HttpInEvent.class.getName());
	}

}
