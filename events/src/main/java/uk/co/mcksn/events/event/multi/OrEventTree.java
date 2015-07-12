package uk.co.mcksn.events.event.multi;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public class OrEventTree extends OperatorEventTree {

	public OrEventTree(EventTreeable[] leaves) {
		super();
		this.leaves.addAll(Arrays.asList(leaves));
	}

	protected EventState calculateState() {
		EventState stateSoFar = EventState.IN_PROGRESS;
		for (EventTreeable eventTree : leaves) {

			if (eventTree instanceof Event) {
				if (((Event) eventTree).getState().equals(EventState.OCCURRED)) {
					return EventState.OCCURRED;
				}
			}

			if (eventTree.getComplexEvent().getState().equals(EventState.OCCURRED)) {
				return EventState.OCCURRED;

			}
		}
		return stateSoFar;
	}


}
