package uk.co.mcksn.events.event.module.occured;

import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public class EventOccurredModule extends AbstractEventOccuredModule<VerifyPlotable> {

	public EventOccurredModule(VerifyPlotable verifyPlotable) {
		super(verifyPlotable);
	}

	protected EventState getInternalState() {
		return this.state;
	}

	protected VerificationOutcome internalGetVerificationOutcome() {
		return this.verificationStrategyFactory.createVerificationStrategy(verifyPlotable)
				.calculateVerificationOutcome(this.verifyPlotable);
	}

	public void setVerificationStrategyFactory(VerificationStrategyFactory verificationStrategyFactory) {
		this.verificationStrategyFactory = verificationStrategyFactory;
	}

}
