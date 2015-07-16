package uk.co.mcksn.events.event.module.occured;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.verify.NoVerficationPolicy;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public abstract class ComplexEventOccuredModule extends AbstractEventOccuredModule<ComplexEvent> {

	public ComplexEventOccuredModule(ComplexEvent event) {
		super(event);
	}

	protected EventState getInternalState() {
		if (!this.state.equals(EventState.OCCURRED)) {
			this.state = calculateState();
		}

		return state;
	}

	/**
	 * Calculate state of all children by recursively calling
	 * {@link AbstractEventOccuredModule#getState()}
	 */
	protected EventState calculateState() {
		Collection<EventState> eventStatesFromLeaves = new ArrayList<EventState>();
		for (VerifyPlotable aVerifyPlotable : this.verifyPlotable.getChildren()) {
			eventStatesFromLeaves.add(aVerifyPlotable.getEventOccurredModule().getState());
		}
		return internalCalculateState(eventStatesFromLeaves);
	}

	/**
	 * Assess the events of all leafs (given event occurred). Finds overall
	 * success based on {@link VerificationOutcome} precedence.
	 * 
	 * @param verificationStrategyFactory
	 * @return
	 */
	private VerificationOutcome calculateVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {

		Collection<VerificationOutcome> verificationOutcomesFromLeaves = new ArrayList<VerificationOutcome>(
				this.verifyPlotable.getChildren().size());

		for (VerifyPlotable aVerifyPlotable : this.verifyPlotable.getChildren()) {

			verificationOutcomesFromLeaves
					.add(aVerifyPlotable.getEventOccurredModule().getVerificationOutcome());
		}
		return VerificationOutcome.getOverallVerificationOutcome(verificationOutcomesFromLeaves);

	}

	protected VerificationOutcome internalGetVerificationOutcome() {
		if (this.verificationOutcome.equals(VerificationOutcome.UNKOWN)) {
			verificationOutcome = calculateVerificationOutcome(this.verificationStrategyFactory);
		}

		return this.verificationOutcome;
	}

	public void setVerificationStrategyFactory(VerificationStrategyFactory verificationStrategyFactory) {
		
		this.verificationStrategyFactory = verificationStrategyFactory;
		
		for (VerifyPlotable aVerifyPlotable : this.verifyPlotable.getChildren()) {
			aVerifyPlotable.getEventOccurredModule().setVerificationStrategyFactory(this.verificationStrategyFactory);
		}
	}

	protected abstract EventState internalCalculateState(Collection<EventState> eventStates);

}
