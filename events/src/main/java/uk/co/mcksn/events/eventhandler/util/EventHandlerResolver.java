package uk.co.mcksn.events.eventhandler.util;

import static com.google.common.collect.Iterables.*;

import java.util.List;

import com.google.common.base.Predicate;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.EventHandlerable;
import uk.co.mcksn.events.execption.NoApplicableHandlerFoundException;

public class EventHandlerResolver {

	/**
	 * 
	 * @param eventHandlerTypeToReturn
	 * @param event
	 * @param eventHandlerable
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T findApplicableHandler(Class<T> eventHandlerTypeToReturn, Event event,
			List<? extends EventHandlerable> eventHandlerable) {
		EventHandlerable appropriateEventHandler = find(eventHandlerable, matchLandscape(event), null);
		if (appropriateEventHandler != null
				&& eventHandlerTypeToReturn.isAssignableFrom(appropriateEventHandler.getClass())) {
			return (T) appropriateEventHandler;
		}
		throw new NoApplicableHandlerFoundException();
	}

	private static Predicate<EventHandlerable> matchLandscape(final Event event) {
		return new Predicate<EventHandlerable>() {
			public boolean apply(EventHandlerable eventHandler) {
				if (eventHandler.getEventType().isInstance(event)) {
					return true;
				} else
					return false;
			}

		};
	}
}
