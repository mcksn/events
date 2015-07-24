package uk.co.mcksn.events.eventhandler.util;

import static com.google.common.collect.Iterables.*;

import java.util.List;

import com.google.common.base.Predicate;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;

public class EventHandlerResolver {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T findApplicableHandler(Class<T> eventHandlerTypeToReturn, Event event, List<AbstractEventHandler> abstractEventHandlers) {
		AbstractEventHandler appropriateEventHandler = find(abstractEventHandlers, matchLandscape(event), null);
		if (appropriateEventHandler != null && eventHandlerTypeToReturn.isAssignableFrom(appropriateEventHandler.getClass())) {
			return (T) appropriateEventHandler;
		} //TODO throw exception
		return null;
	}

	private static Predicate<AbstractEventHandler> matchLandscape(final Event event) {
		return new Predicate<AbstractEventHandler>() {
			public boolean apply(AbstractEventHandler eventHandler) {
				if (eventHandler.getEventType().isInstance(event)) {
					return true;
				} else
					return false;
			}

		};
	}
}
