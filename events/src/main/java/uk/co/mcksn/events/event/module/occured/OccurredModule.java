package uk.co.mcksn.events.event.module.occured;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.type.Verifyable;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategyFactory;

@SuppressWarnings("rawtypes")
public class OccurredModule extends AbstractOccuredModule<Verifyable> {

	public OccurredModule(Verifyable verifyable) {
		super(verifyable);
	}

	protected EventState getInternalState() {
		return this.state;
	}

	protected VerificationOutcome internalGetVerificationOutcome() {
		return this.verificationStrategyFactory.createVerificationStrategy(verifyable)
				.calculateVerificationOutcome(this.verifyable);
	}

	public void setVerificationStrategyFactory(VerificationStrategyFactory verificationStrategyFactory) {
		this.verificationStrategyFactory = verificationStrategyFactory;
	}

}
