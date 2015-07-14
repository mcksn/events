package uk.co.mcksn.events.event.module.occured;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;

public class AndEventOccurredModule extends ComplexEventOccuredModule {

	public AndEventOccurredModule(ComplexEvent event) {
		super(event);
	}

	protected EventState calculateState() {
		EventState stateSoFar = EventState.OCCURRED;
		for (Event aEvent : event.getLeaves()) {

			if (!(aEvent instanceof ComplexEvent)) {
				if (aEvent.getState().equals(EventState.OCCURRED)) {
					stateSoFar = EventState.IN_PROGRESS;
				}
			}

			if (!aEvent.getState().equals(EventState.OCCURRED)) {
				stateSoFar = EventState.IN_PROGRESS;

			}
		}
		return stateSoFar;
	}

}
