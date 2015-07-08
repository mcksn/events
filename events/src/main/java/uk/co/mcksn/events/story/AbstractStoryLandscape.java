package uk.co.mcksn.events.story;

import java.lang.reflect.ParameterizedType;

import com.github.tomakehurst.wiremock.verification.VerificationResult;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.multi.EventLeaf;
import uk.co.mcksn.events.event.multi.EventTree;
import uk.co.mcksn.events.exception.VerificationFailedException;

public abstract class AbstractStoryLandscape<E extends Event> {

	private Story story = null;
	
	protected abstract void when(Event event);
	
	protected abstract void when(EventTree event);

	protected abstract void verify(Event event);

	protected abstract void simulate(Event event);

	protected abstract void expect(Event event);

	@SuppressWarnings("unchecked")
	public Class<E> getAssociatedEventType() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected void setStory(Story story) {
		this.story = story;
	}

	protected ThreadSafeEventQueueWorker getEventQueueWorker() {
		return story.getEventQueueWorker();
	}

}
