package uk.co.mcksn.events.event.module.occured;

import java.util.Collection;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.plot.VerifyPlotable;

public class OrEventOccurredModule extends ComplexEventOccuredModule {

	public OrEventOccurredModule(ComplexEvent event) {
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
