package uk.co.mcksn.events.eventhandler.strategy;

import java.util.ArrayList;
import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.EventHandlerable;

@SuppressWarnings("rawtypes")
public class AbstractStrategyFactory {
	
	EventHandlerResolver eventHandlerResolver = new EventHandlerResolver();

	protected List<EventHandlerable> availableEventHandlers = new ArrayList<EventHandlerable>();

	public AbstractStrategyFactory(List<EventHandlerable> availableEventHandlers) {
		this.availableEventHandlers = availableEventHandlers;
	}

	protected <T> T findSuitableLandscape(Class<T> returnType, Event event) {
		return EventHandlerResolver.findApplicableHandler(returnType, event, availableEventHandlers);
	}
	
	protected EventHandlerable findSuitableLandscape(Event event) {
		return EventHandlerResolver.findApplicableHandler(AbstractEventHandler.class, event, availableEventHandlers);
	}


}