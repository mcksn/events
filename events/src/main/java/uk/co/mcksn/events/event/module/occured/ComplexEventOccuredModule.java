package uk.co.mcksn.events.event.module.occured;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.strategy.VerificationStrategy;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public abstract class ComplexEventOccuredModule extends AbstractEventOccuredModule<ComplexEvent> {

	public ComplexEventOccuredModule(ComplexEvent event) {
		super(event);
	}
	
	public EventState getUpdatedState() {
		if (event.getState().equals(EventState.OCCURRED)) {
			return EventState.OCCURRED;
		} else {
			event.setState(calculateState());
		}
		return event.getState();
	}
	
	protected abstract EventState calculateState();


	/**
	 * Assess the events of all leafs (given event occurred). Finds overall
	 * success based on {@link VerificationOutcome} precedence.
	 * 
	 * @param verificationStrategyFactory
	 * @return
	 */
	private VerificationOutcome calculateVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {

		Collection<VerificationOutcome> verificationOutcomesFromLeaves = new ArrayList<VerificationOutcome>(
				event.getLeaves().size());

		for (Event aEvent : event.getLeaves()) {

			if (!(aEvent instanceof ComplexEvent)) {
				VerificationStrategy verificationStrategy = verificationStrategyFactory
						.createVerificationStrategy((Event) aEvent);

				verificationOutcomesFromLeaves.add(verificationStrategy.calculateVerificationOutcome((Event) aEvent));
			}
		}
		return VerificationOutcome.getOverallVerificationOutcome(verificationOutcomesFromLeaves);
	}

	public VerificationOutcome getUpdatedVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {
		VerificationOutcome outcome = event.getVerificationOutcome();
		if (!outcome.equals(VerificationOutcome.UNKOWN)) {
			return outcome;
		} else {
			event.setVerificationOutcome(calculateVerificationOutcome(verificationStrategyFactory));
		}
		return event.getEventOccurredModule().getUpdatedVerificationOutcome(verificationStrategyFactory);
	}
	

}
