package uk.co.mcksn.events.event.strategy;

import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public interface VerificationStrategy {

	public VerificationOutcome calculateVerificationOutcome(VerifyPlotable verifyPlotables);

}
