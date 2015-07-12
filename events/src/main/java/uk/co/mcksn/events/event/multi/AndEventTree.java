package uk.co.mcksn.events.event.multi;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;

public class AndEventTree extends OperatorEventTree implements EventTreeable {

	public AndEventTree(EventTreeable[] leaves) {
		super();
		this.leaves.addAll(Arrays.asList(leaves));
	}

	protected EventState calculateState() {
		EventState stateSoFar = EventState.OCCURRED;
		for (EventTreeable eventTree : leaves) {

			if (eventTree instanceof Event) {
				if (!((Event) eventTree).getState().equals(EventState.OCCURRED)) {
					stateSoFar = EventState.IN_PROGRESS;
				}
			}

			if (!eventTree.getComplexEvent().getState().equals(EventState.OCCURRED)) {
				stateSoFar = EventState.IN_PROGRESS;

			}
		}
		return stateSoFar;
	}

}
