package uk.co.mcksn.events.event.module.occured;

import java.util.Collection;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;

public class AndOccurredModule extends OccuredComplexModule {

	public AndOccurredModule(ComplexEvent event) {
		super(event);
	}


	/**
	 * If at least one not {@link EventState#OCCURRED} then return
	 * {@link EventState#IN_PROGRESS}. If not return {@link EventState#OCCURRED}
	 * 
	 * @param eventStates
	 * @return
	 */
	protected EventState internalCalculateState(Collection<EventState> eventStates) {
		EventState stateSoFar = EventState.OCCURRED;
		for (EventState aEventState : eventStates) {

			if (!aEventState.equals(EventState.OCCURRED)) {
				stateSoFar = EventState.IN_PROGRESS;
			}
		}
		return stateSoFar;

	}

}
