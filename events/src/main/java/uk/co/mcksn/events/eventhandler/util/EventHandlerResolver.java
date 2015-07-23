package uk.co.mcksn.events.eventhandler.util;

import static com.google.common.collect.Iterables.find;

import java.util.List;

import com.google.common.base.Predicate;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;

public class EventHandlerResolver {

	public static AbstractEventHandler<? extends Event> findApplicableLandscape(Event event, List<AbstractEventHandler<? extends Event>> landscapes) {
		AbstractEventHandler<? extends Event> matchingLanscape = find(landscapes, matchLandscape(event), null);
		return matchingLanscape;
	}


	private static Predicate<AbstractEventHandler<? extends Event>> matchLandscape(final Event event) {
		return new Predicate<AbstractEventHandler<? extends Event>>() {
			public boolean apply(AbstractEventHandler<? extends Event> landscape) {
				if (landscape.getAssociatedEventType().isInstance(event)) {
					return true;
				} else
					return false;
			}

		};
	}
}
