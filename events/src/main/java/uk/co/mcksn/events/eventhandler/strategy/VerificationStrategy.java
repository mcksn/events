package uk.co.mcksn.events.eventhandler.strategy;

import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.type.Verifyable;

@SuppressWarnings("rawtypes")
public interface VerificationStrategy {

	public VerificationOutcome calculateVerificationOutcome(Verifyable verifyables);

}
