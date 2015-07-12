package uk.co.mcksn.events.story;

import java.lang.reflect.ParameterizedType;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.multi.EventTreeable;
import uk.co.mcksn.events.event.strategy.VerificationStrategy;

public abstract class AbstractStoryLandscape<E extends Event> {

	private Story story = null;

	
	@SuppressWarnings("unchecked")
	public Class<E> getAssociatedEventType() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected abstract void when(Event event);
	
	protected abstract void when(EventTreeable event);

	protected abstract void simulate(Event event);

	protected abstract void expect(Event event);
	
	public abstract VerificationStrategy getVerificationStrategy();

	protected void setStory(Story story) {
		this.story = story;
	}

	protected ThreadSafeEventQueueWorker getEventQueueWorker() {
		return story.getEventQueueWorker();
	}


}
