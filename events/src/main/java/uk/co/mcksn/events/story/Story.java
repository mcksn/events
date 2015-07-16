package uk.co.mcksn.events.story;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.landscape.LandscapeResolver;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.Plot;
import uk.co.mcksn.events.plot.SimulatePlotable;
import uk.co.mcksn.events.plot.WhenPlotable;

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

	private VerificationStrategyFactory verificationStrategyFactory = new VerificationStrategyFactory(
			availableLandscapes);
	private RegisterForWaitStrategyFactory registerForWaitStrategyFactory = new RegisterForWaitStrategyFactory(
			availableLandscapes);

	public <SimulatePlotableEvent extends Event & SimulatePlotable> ThenStory simulate(
			SimulatePlotableEvent simulatePlotableEvent) {

		logPlot(Plot.SIMULATE, simulatePlotableEvent);
		
		simulatePlotableEvent.getEventOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventQueueWorker.add(simulatePlotableEvent);

		findSuitableLandscape(simulatePlotableEvent).simulate(simulatePlotableEvent);

		return new ThenStory(this);

	}

	public <WhenPlotableEvent extends Event & WhenPlotable> OccursThenStory when(WhenPlotableEvent whenPlotableEvent) {

		logPlot(Plot.WHEN,whenPlotableEvent);
		


		whenPlotableEvent.getTreeModule().setParentsOfAllChildren(null);

		whenPlotableEvent.getWaitModule().registerForWait(registerForWaitStrategyFactory);

		whenPlotableEvent.getEventOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventQueueWorker.add(whenPlotableEvent);

		whenPlotableEvent.getWaitModule().doWait();

		return new OccursThenStory(this);

	}

	public <ExpectPlotableEvent extends Event & ExpectPlotable> ThenStory expect(ExpectPlotableEvent expectPlotableEvent) {

		logPlot(Plot.EXPECT,expectPlotableEvent);
		

		expectPlotableEvent.getTreeModule().setParentsOfAllChildren(null);

		expectPlotableEvent.getWaitModule().registerForWait(registerForWaitStrategyFactory);

		expectPlotableEvent.getEventOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventQueueWorker.add(expectPlotableEvent);

		return new ThenStory(this);
	}

	public void logStorySoFar() {
		// todo
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
