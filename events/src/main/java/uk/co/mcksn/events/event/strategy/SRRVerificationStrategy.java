package uk.co.mcksn.events.event.strategy;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public class SRRVerificationStrategy implements VerificationStrategy {

	@Override
	public VerificationOutcome calculateVerificationOutcome(Event event) {
		if (!event.getState().equals(EventState.OCCURRED))
			return VerificationOutcome.UNKOWN;

		ServerReceivesRequestEvent srrEvent = cast(event);

		boolean success = srrEvent.getVerificationPolicy().getRequestPattern()
				.isMatchedBy(srrEvent.getResult().getLoggedRequest());

		if (success)
			return VerificationOutcome.SUCCESS;
		else
			return VerificationOutcome.FAULURE;
	}

	private static ServerReceivesRequestEvent cast(Event event) {
		if (event instanceof ServerReceivesRequestEvent) {
			return (ServerReceivesRequestEvent) event;
		}
		throw new RuntimeException("Can not cast object to " + ServerReceivesRequestEvent.class.getName());
	}
}
