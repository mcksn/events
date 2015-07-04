package uk.co.mcksn.events.story;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.exception.VerificationFailedException;

class StoryDecorator {

	private Story story = null;
	private ThenStory thenStory = null;
	private OccursThenStory occursThenStory = null;

	protected StoryDecorator(Story story) {
		super();
		this.story = story;
	}

	protected Story getStory() {
		return story;
	}

	protected void setStory(Story story) {
		this.story = story;
	}

	protected OccursThenStory getOccursThenStory() {
		if (occursThenStory == null) {
			occursThenStory = new OccursThenStory(story);
		}
		return occursThenStory;
	}

	protected ThenStory getThenStory() {
		if (thenStory == null) {
			thenStory = new ThenStory(story);
		}
		return thenStory;
	}

	public Story and_the_story_continues()
	{
		return getStory();
	}
	
	public Story logStorySoFar()
	{
		getStory().logStorySoFar();
		return getStory();
	}
	
	public Story logWhatActuallyHappeneds()
	{
		getStory().logWhatActuallyHappened();
		return getStory();
	}

}
