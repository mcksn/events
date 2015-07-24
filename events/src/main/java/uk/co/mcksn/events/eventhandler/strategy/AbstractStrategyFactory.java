package uk.co.mcksn.events.eventhandler.strategy;

import java.util.ArrayList;
import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;

@SuppressWarnings("rawtypes")
public class AbstractStrategyFactory {
	
	EventHandlerResolver eventHandlerResolver = new EventHandlerResolver();

	protected List<AbstractEventHandler> availableEventHandlers = new ArrayList<AbstractEventHandler>();

	public AbstractStrategyFactory(List<AbstractEventHandler> availableEventHandlers) {
		this.availableEventHandlers = availableEventHandlers;
	}

	protected <T> T findSuitableLandscape(Class<T> returnType, Event event) {
		return EventHandlerResolver.findApplicableHandler(returnType, event, availableEventHandlers);
	}
	
	protected AbstractEventHandler findSuitableLandscape(Event event) {
		return EventHandlerResolver.findApplicableHandler(AbstractEventHandler.class, event, availableEventHandlers);
	}


}