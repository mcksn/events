package uk.co.mcksn.events.event.module.occured;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.eventhandler.strategy.AbstractStrategyFactory;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.type.Verifyable;

@SuppressWarnings("rawtypes")
public abstract class OccuredComplexModule extends AbstractOccuredModule<ComplexEvent> {

	public OccuredComplexModule(ComplexEvent event) {
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
	 * {@link AbstractOccuredModule#getState()}
	 */
	protected EventState calculateState() {
		Collection<EventState> eventStatesFromLeaves = new ArrayList<EventState>();
		for (Verifyable aVerifyable : this.verifyPlotable.getChildren()) {
			eventStatesFromLeaves.add(aVerifyable.getOccurredModule().getState());
		}
		return internalCalculateState(eventStatesFromLeaves);
	}

	/**
	 * Assess the events of all leafs (given event occurred). Finds overall
	 * success based on {@link VerificationOutcome} precedence.
	 * 
	 * @param verificationAbstractStrategyFactory
	 * @return
	 */
	private VerificationOutcome calculateVerificationOutcome(
			AbstractStrategyFactory verificationAbstractStrategyFactory) {

		Collection<VerificationOutcome> verificationOutcomesFromLeaves = new ArrayList<VerificationOutcome>(
				this.verifyPlotable.getChildren().size());

		for (Verifyable aVerifyable : this.verifyPlotable.getChildren()) {

			verificationOutcomesFromLeaves.add(aVerifyable.getOccurredModule().getVerificationOutcome());
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

		for (Verifyable aVerifyable : this.verifyPlotable.getChildren()) {
			aVerifyable.getOccurredModule().setVerificationStrategyFactory(this.verificationStrategyFactory);
		}
	}

	protected abstract EventState internalCalculateState(Collection<EventState> eventStates);

}
