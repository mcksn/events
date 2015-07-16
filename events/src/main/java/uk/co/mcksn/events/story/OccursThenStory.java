package uk.co.mcksn.events.story;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.plot.SimulatePlotable;

public class OccursThenStory extends StoryDecorator {

	OccursThenStory(Story story) {
		super(story);
	}

	public <SimulatePlotableEvent extends Event & SimulatePlotable> ThenStory  occursThenSimulate(SimulatePlotableEvent simulatePlotableEvent) {
		getStory().simulate(simulatePlotableEvent);
		return getThenStory();
	}

}
