package uk.co.mcksn.events.event.module.occured;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;

public class OrEventOccurredModule extends ComplexEventOccuredModule {

	public OrEventOccurredModule(ComplexEvent event) {
		super(event);
	}

	protected EventState calculateState() {
		EventState stateSoFar = EventState.IN_PROGRESS;
		for (Event aEvent : event.getLeaves()) {

			if (!(aEvent instanceof ComplexEvent)) {
				if (aEvent.getState().equals(EventState.OCCURRED)) {
					return EventState.OCCURRED;
				}
			}

			if (aEvent.getState().equals(EventState.OCCURRED)) {
				return EventState.OCCURRED;

			}
		}
		return stateSoFar;
	}

}
