package uk.co.mcksn.events.story;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.exception.VerificationNtSuccessfulException;
import uk.co.mcksn.events.landscape.LandscapeResolver;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.Plot;
import uk.co.mcksn.events.plot.SimulatePlotable;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.WhenPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

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
		newStory.verificationStrategyFactory = new VerificationStrategyFactory(newStory.availableLandscapes);
		return newStory;
	}

	private List<AbstractStoryLandscape<? extends Event>> availableLandscapes = new ArrayList<AbstractStoryLandscape<? extends Event>>();

	private ThreadSafeEventQueueWorker eventQueueWorker = new ThreadSafeEventQueueWorker();

	private VerificationStrategyFactory verificationStrategyFactory = null;

	public ThenStory simulate(SimulatePlotable event) {
		Event castEvent = castToEvent(event);
		logPlot(Plot.SIMULATE, castEvent);
		eventQueueWorker.add(castEvent);
		findSuitableLandscape(castEvent).simulate(castEvent);
		return new ThenStory(this);

	}

	public ThenStory verify(VerifyPlotable event) {

		if (event instanceof ComplexEvent) {
			internalWhen((ComplexEvent) event);
		}

		if (event instanceof EventTreeable) {
			internalWhen(((EventTreeable) event).getComplexEvent());
		}

		if (event instanceof Event) {
			Event castEvent = castToEvent(event);

			logPlot(Plot.VERIFY, castEvent);

			VerificationOutcome outcome = castEvent.getVerificationOutcome();
			boolean verifyAndContinueStory = castEvent.getVerificationPolicy().verifyAndContinueStory();
			if (outcome.equals(!(outcome == VerificationOutcome.SUCCESS) && !verifyAndContinueStory)) {
				throw new VerificationNtSuccessfulException(castEvent);
			}

		}

		return new ThenStory(this);
	}

	public OccursThenStory when(WhenPlotable event) {

		// TODO Shouldnt assume all events in tree will be of same time. Do
		// similar to verify
		return new OccursThenStory(this);

	}

	private void internalWhen(ComplexEvent complexEvent) {
		logPlot(Plot.WHEN, complexEvent);
		
		complexEvent.
		complexEvent.setParentsOfAllChildren(null);
		
		((WhenPlotable) event).doWait();

	}

	private void internalVerify(ComplexEvent complexEvent) {
		logPlot(Plot.VERIFY, complexEvent);
		VerificationOutcome outcome = complexEvent.getUpdatedVerificationOutcome(verificationStrategyFactory);
		boolean verifyAndContinueStory = complexEvent.getVerificationPolicy().verifyAndContinueStory();
		if (outcome.equals(!(outcome == VerificationOutcome.SUCCESS) && !verifyAndContinueStory)) {
			throw new VerificationNtSuccessfulException(complexEvent);
		}

	}

	public ThenStory expect(ExpectPlotable event) {
		Event castEvent = castToEvent(event);
		logPlot(Plot.EXPECT, castEvent);
		eventQueueWorker.add(castEvent);
		findSuitableLandscape(castEvent).expect(castEvent);
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

	private Event castToEvent(Object obj) {
		if (obj instanceof Event) {
			return (Event) obj;
		} // TODO Create exception for this scenario
		throw new RuntimeException("Object passed into story is not of type Event");
	}
}
