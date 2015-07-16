package uk.co.mcksn.events.story;

import java.lang.reflect.ParameterizedType;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.event.strategy.VerificationStrategy;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.SimulatePlotable;
import uk.co.mcksn.events.plot.WhenPlotable;

public abstract class AbstractStoryLandscape<E extends Event> {

	private Story story = null;

	@SuppressWarnings("unchecked")
	public Class<E> getAssociatedEventType() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected abstract <SimulatePlotableEvent extends Event & SimulatePlotable> void simulate(
			SimulatePlotableEvent simulatePlotableEvent);

	public abstract VerificationStrategy getVerificationStrategy();

	public abstract RegisterForWaitStrategy getRegisterForWaitStrategy();

	protected void setStory(Story story) {
		this.story = story;
	}

	protected ThreadSafeEventQueueWorker getEventQueueWorker() {
		return story.getEventQueueWorker();
	}

}
