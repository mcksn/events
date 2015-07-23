package uk.co.mcksn.events.event.module.occured;

import java.util.Collection;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;

public class OrOccurredModule extends ComplexOccuredModule {

	public OrOccurredModule(ComplexEvent event) {
		super(event);
	}
	
	/**
	 * If at least one {@link EventState#OCCURRED} then return
	 * {@link EventState#OCCURRED}.
	 * 
	 * @param eventStates
	 * @return
	 */
	protected EventState internalCalculateState(Collection<EventState> eventStates) {
		EventState stateSoFar = EventState.IN_PROGRESS;
		for (EventState aEventState : eventStates) {

			if (aEventState.equals(EventState.OCCURRED)) {
				stateSoFar = EventState.OCCURRED;
			}
		}
		return stateSoFar;

	}

}
