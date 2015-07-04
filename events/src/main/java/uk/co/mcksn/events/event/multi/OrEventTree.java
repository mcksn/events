package uk.co.mcksn.events.event.multi;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;

public class OrEventTree extends OperatorEventTree {

	public OrEventTree(EventTree[] leaves) {
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
		EventState stateSoFar = EventState.IN_PROGRESS;
		for (EventTree eventTree : leaves) {

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
