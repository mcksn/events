package uk.co.mcksn.events.story;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.multi.EventTree;
import uk.co.mcksn.events.exception.VerificationFailedException;

public class ThenStory extends StoryDecorator {

	ThenStory(Story story) {
		super(story);
	}

	public ThenStory thenSimulate(Event event) {
		getStory().simulate(event);
		return this;
	}
	
	public ThenStory then_expect(Event event) {
		getStory().simulate(event);
		return this;
	}

	public ThenStory then_verify(Event event) throws VerificationFailedException {
		getStory().verify(event);
		return this;
	}
	
	public OccursThenStory and_when(EventTree event) throws VerificationFailedException {
		getStory().when(event);
		return getOccursThenStory();
	}
	
}