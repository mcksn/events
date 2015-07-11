package uk.co.mcksn.events.story;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventLeaf;
import uk.co.mcksn.events.event.multi.EventTree;
import uk.co.mcksn.events.landscape.LandscapeResolver;
import uk.co.mcksn.events.plot.Plot;

/**
 * A story is a sequence of {@link Event}s that will perform a testable
 * scenario.
 * 
 * @author mackson
 *
 */
public class Story {

	private static final Logger LOGGER = LoggerFactory.getLogger(Story.class);

	/**
	 * Specify the landscapes the story will be set in and start the story
	 * 
	 * @param abstractStoryLandscape
	 * @return
	 */
	public static Story given(AbstractStoryLandscape<? extends Event> abstractStoryLandscape) {

		Story newStory = new Story();
		for (AbstractStoryLandscape storyLandscape : Arrays.asList(abstractStoryLandscape)) {
			storyLandscape.setStory(newStory);
		}

		newStory.availableLandscapes.addAll(Arrays.asList(abstractStoryLandscape));
		return newStory;
	}

	private List<AbstractStoryLandscape<? extends Event>> availableLandscapes = new ArrayList<AbstractStoryLandscape<? extends Event>>();

	private ThreadSafeEventQueueWorker eventQueueWorker = new ThreadSafeEventQueueWorker();

	public ThenStory simulate(Event event) {
		logPlot(Plot.SIMULATE, event);
		eventQueueWorker.add(event);
		findSuitableLandscape(event).simulate(event);
		return new ThenStory(this);

	}

	public ThenStory verify(Event event) {
		logPlot(Plot.VERIFY, event);
		findSuitableLandscape(event).verify(event);
		return new ThenStory(this);
	}

	public OccursThenStory when(EventTree eventTree) {
		if (eventTree instanceof EventLeaf) {
			Event event = (Event) eventTree;

			logPlot(Plot.WHEN, event);

			eventQueueWorker.add(event);
			findSuitableLandscape(event).when(event);
		} else {
			internalWhen(eventTree.getComplexEvent());
		}
		return new OccursThenStory(this);
	}

	private void internalWhen(ComplexEvent complexEvent) {
		logPlot(Plot.WHEN, complexEvent);
		complexEvent.setParentsOfAllChildren(null);
	}

	public OccursThenStory when(ComplexEvent complexEvent) {
		internalWhen(complexEvent);
		return new OccursThenStory(this);
	}

	public ThenStory expect(Event event) {
		logPlot(Plot.EXPECT, event);
		eventQueueWorker.add(event);
		findSuitableLandscape(event).expect(event);
		return new ThenStory(this);
	}

	public void logStorySoFar() {
		System.out.println(eventQueueWorker.copy().toString());
	}

	public void logWhatActuallyHappened() {
		// todo
	}

	ThreadSafeEventQueueWorker getEventQueueWorker() {
		return eventQueueWorker;
	}

	void setEventQueueWorker(ThreadSafeEventQueueWorker eventQueueWorker) {
		this.eventQueueWorker = eventQueueWorker;
	}

	private AbstractStoryLandscape<? extends Event> findSuitableLandscape(Event event) {
		return LandscapeResolver.findApplicableLandscape(event, availableLandscapes);
	}

	private void logPlot(Plot plot, Event event) {
		LOGGER.info("<<" + plot.toString() + ">>: " + event.getName());
	}

}
