package uk.co.mcksn.events.landscape;

import static com.google.common.collect.Iterables.find;

import java.util.List;

import com.google.common.base.Predicate;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.story.AbstractStoryLandscape;

public class LandscapeResolver {

	public static AbstractStoryLandscape<? extends Event> findApplicableLandscape(Event event, List<AbstractStoryLandscape<? extends Event>> landscapes) {
		AbstractStoryLandscape<? extends Event> matchingLanscape = find(landscapes, matchLandscape(event), null);
		return matchingLanscape;
	}


	private static Predicate<AbstractStoryLandscape<? extends Event>> matchLandscape(final Event event) {
		return new Predicate<AbstractStoryLandscape<? extends Event>>() {
			public boolean apply(AbstractStoryLandscape<? extends Event> landscape) {
				if (landscape.getAssociatedEventType().isInstance(event)) {
					return true;
				} else
					return false;
			}

		};
	}
}
