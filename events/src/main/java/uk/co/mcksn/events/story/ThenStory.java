package uk.co.mcksn.events.story;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.exception.VerificationNtSuccessfulException;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.SimulatePlotable;
import uk.co.mcksn.events.plot.WhenPlotable;

public class ThenStory extends StoryDecorator {

	ThenStory(Story story) {
		super(story);
	}

	public <SimulatePlotableEvent extends Event & SimulatePlotable> ThenStory then_simulate(SimulatePlotableEvent simulatePlotableEvent) {
		getStory().simulate(simulatePlotableEvent);
		return this;
	}

	public <ExpectPlotableEvent extends Event & ExpectPlotable> ThenStory then_expect(ExpectPlotableEvent expectPlotableEvent) {
		getStory().expect(expectPlotableEvent);
		return this;
	}

	public <WhenPlotableEvent extends Event & WhenPlotable> OccursThenStory and_when(WhenPlotableEvent whenPlotableEvent)
			throws VerificationNtSuccessfulException {
		getStory().when(whenPlotableEvent);
		return getOccursThenStory();
	}

}