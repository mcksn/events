package uk.co.mcksn.events.story;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.exception.VerificationNtSuccessfulException;
import uk.co.mcksn.events.plot.SimulatePlotable;
import uk.co.mcksn.events.plot.VerifyPlotable;

public class OccursThenStory extends StoryDecorator {

	OccursThenStory(Story story) {
		super(story);
	}

	public ThenStory occursThenSimulate(SimulatePlotable event) {
		getStory().simulate(event);
		return getThenStory();
	}

	public ThenStory occurs_then_verify(VerifyPlotable event) throws VerificationNtSuccessfulException {
		getStory().verify(event);
		return getThenStory();
	}
	

}
