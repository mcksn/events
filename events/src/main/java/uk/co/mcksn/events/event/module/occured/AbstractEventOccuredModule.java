package uk.co.mcksn.events.event.module.occured;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public abstract class AbstractEventOccuredModule<E extends Event> {

	protected E event = null;

	public AbstractEventOccuredModule(E event) {
		super();
		this.event = event;
	}

	public abstract EventState getUpdatedState();

	public abstract VerificationOutcome getUpdatedVerificationOutcome(
			VerificationStrategyFactory verificationStrategyFactory);

}
