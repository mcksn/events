package uk.co.mcksn.events.eventhandler.strategy;

import java.util.ArrayList;
import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;

public class AbstractStrategyFactory {

	protected List<AbstractEventHandler<? extends Event>> availableEventHandlers = new ArrayList<AbstractEventHandler<? extends Event>>();

	public AbstractStrategyFactory(List<AbstractEventHandler<? extends Event>> availableEventHandlers) {
		this.availableEventHandlers = availableEventHandlers;
	}

	protected AbstractEventHandler<? extends Event> findSuitableLandscape(Event event) {
		return EventHandlerResolver.findApplicableLandscape(event, availableEventHandlers);
	}

}