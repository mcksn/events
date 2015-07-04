package uk.co.mcksn.events.event.multi;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;

public class AndEventTree extends OperatorEventTree implements EventTree {

	public AndEventTree(EventTree[] leaves) {
		super();
		this.leaves.addAll(Arrays.asList(leaves));
	}

	public EventState getUpdatedState() {
		ComplexEvent complexEvent = getComplexEvent();
		if (complexEvent.getState().equals(EventState.OCCURRED)) {
			return EventState.OCCURRED;
		} else {
			complexEvent.setState(calculateState());
		}
		return complexEvent.getState();
	}

	private EventState calculateState() {
		EventState stateSoFar = EventState.OCCURRED;
		for (EventTree eventTree : leaves) {

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
