package uk.co.mcksn.events.event.module.occured;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.type.Verifyable;

@SuppressWarnings("rawtypes")
public class OccurredModule<VerifybleEvent extends Event & Verifyable> extends AbstractOccuredModule<VerifybleEvent> {

	public OccurredModule(VerifybleEvent verifyable) {
		super(verifyable);
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
