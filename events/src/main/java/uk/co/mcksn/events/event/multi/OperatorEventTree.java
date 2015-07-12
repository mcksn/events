package uk.co.mcksn.events.event.multi;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.strategy.VerificationStrategy;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public abstract class OperatorEventTree implements EventTreeable {

	private ComplexEvent complexEvent = null;
	private EventTreeable parent;
	protected Collection<EventTreeable> leaves = new ArrayList<EventTreeable>();

	public EventTreeable getParent() {
		return parent;
	}

	public void setParent(EventTreeable parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(EventTreeable rootParent) {
		setParent(rootParent);
		for (EventTreeable eventTree : leaves) {
			eventTree.setParent(this);
		}
	}

	public ComplexEvent getComplexEvent() {
		if (complexEvent == null)
			return new ComplexEvent((EventTreeable) this);
		else
			return complexEvent;
	}

	@Override
	public void doNotify() {
		complexEvent.doNotify();

	}

	@Override
	public void doWait() {
		complexEvent.doWait();

	}

	@Override
	public void setTimeout(Long timeout) {
		complexEvent.setTimeout(timeout);

	}

	protected abstract EventState calculateState();

	public EventState getUpdatedState() {
		ComplexEvent complexEvent = getComplexEvent();
		if (complexEvent.getState().equals(EventState.OCCURRED)) {
			return EventState.OCCURRED;
		} else {
			complexEvent.setState(calculateState());
		}
		return complexEvent.getState();
	}

	/**
	 * Assess the events of all leafs (given event occurred). Finds overall
	 * success based on {@link VerificationOutcome} precedence.
	 * 
	 * @param verificationStrategyFactory
	 * @return
	 */
	public VerificationOutcome calculateVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {

		Collection<VerificationOutcome> verificationOutcomesFromLeaves = new ArrayList<VerificationOutcome>(
				leaves.size());

		for (EventTreeable eventTree : leaves) {

			if (eventTree instanceof Event) {
				VerificationStrategy verificationStrategy = verificationStrategyFactory
						.createVerificationStrategy((Event) eventTree);

				verificationOutcomesFromLeaves
						.add(verificationStrategy.calculateVerificationOutcome((Event) eventTree));
			}
		}
		return VerificationOutcome.getOverallVerificationOutcome(verificationOutcomesFromLeaves);
	}

	public VerificationOutcome getUpdatedVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {
		ComplexEvent complexEvent = getComplexEvent();
		VerificationOutcome outcome = complexEvent.getVerificationOutcome();
		if (!outcome.equals(VerificationOutcome.UNKOWN)) {
			return outcome;
		} else {
			complexEvent.setVerificationOutcome(calculateVerificationOutcome(verificationStrategyFactory));
		}
		return complexEvent.getUpdatedVerificationOutcome(verificationStrategyFactory);
	}

}