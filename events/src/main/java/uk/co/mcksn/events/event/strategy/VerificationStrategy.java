package uk.co.mcksn.events.event.strategy;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public interface VerificationStrategy {

	public VerificationOutcome calculateVerificationOutcome(Event event);

}
