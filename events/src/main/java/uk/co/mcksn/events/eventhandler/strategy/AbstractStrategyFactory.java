package uk.co.mcksn.events.eventhandler.strategy;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.EventHandlerable;

@SuppressWarnings("rawtypes")
public class AbstractStrategyFactory {

	protected EventHandlerResolver EVENT_HANDLER_RESOLVER;

	protected List<EventHandlerable> availableEventHandlers = new ArrayList<EventHandlerable>();

	public AbstractStrategyFactory(List<EventHandlerable> availableEventHandlers) {
		EVENT_HANDLER_RESOLVER = new EventHandlerResolver();
		this.availableEventHandlers = availableEventHandlers;
	}

	protected <T> T findSuitableLandscape(Class<T> returnType, Event event) {
		return EVENT_HANDLER_RESOLVER.findApplicableHandler(returnType, event, availableEventHandlers);
	}

	protected EventHandlerable findSuitableLandscape(Event event) {
		return EVENT_HANDLER_RESOLVER.findApplicableHandler(AbstractEventHandler.class, event, availableEventHandlers);
	}

	@VisibleForTesting
	void setEventHandlerResolver(EventHandlerResolver resolver) {
		EVENT_HANDLER_RESOLVER = resolver;
	}

}