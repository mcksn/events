package uk.co.mcksn.events.event.module.occured;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public class EventOccurredModule extends AbstractEventOccuredModule<Event> {

	public EventOccurredModule(Event event) {
		super(event);
	}
	
	public EventState getUpdatedState() {
		return event.getState();
	}

	public VerificationOutcome getUpdatedVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {
		return event.getVerificationOutcome();
	}

}
