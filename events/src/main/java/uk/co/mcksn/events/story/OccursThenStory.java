package uk.co.mcksn.events.story;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.exception.VerificationFailedException;

public class OccursThenStory extends StoryDecorator {

	OccursThenStory(Story story) {
		super(story);
	}

	public ThenStory occursThenSimulate(Event event) {
		getStory().simulate(event);
		return getThenStory();
	}

	public ThenStory occurs_then_verify(Event event) throws VerificationFailedException {
		getStory().verify(event);
		return getThenStory();
	}
	

}
