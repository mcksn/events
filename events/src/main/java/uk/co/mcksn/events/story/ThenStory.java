package uk.co.mcksn.events.story;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.exception.VerificationNtSuccessfulException;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.SimulatePlotable;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.WhenPlotable;

public class ThenStory extends StoryDecorator {

	ThenStory(Story story) {
		super(story);
	}

	public ThenStory thenSimulate(SimulatePlotable event) {
		getStory().simulate(event);
		return this;
	}
	
	public ThenStory then_expect(ExpectPlotable event) {
		getStory().expect(event);
		return this;
	}

	public ThenStory then_verify(VerifyPlotable event) throws VerificationNtSuccessfulException {
		getStory().verify(event);
		return this;
	}
	
	public OccursThenStory and_when(WhenPlotable event) throws VerificationNtSuccessfulException {
		getStory().when(event);
		return getOccursThenStory();
	}
	
}