package uk.co.mcksn.events.eventstream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.enumeration.RegisterWithStreamType;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;
import uk.co.mcksn.events.type.Expectable;
import uk.co.mcksn.events.type.Simulateable;
import uk.co.mcksn.events.type.Whenable;

/**
 * A story is a sequence of {@link Event}s that will perform a testable
 * scenario.
 * 
 * @author mackson
 *
 */
public class EventStream {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventStream.class);

	/**
	 * Specify the landscapes the story will be set in and start the story
	 * 
	 * @param abstractEventHandlers
	 * @return
	 */
	public static EventStream given(AbstractEventHandler<? extends Event> abstractEventHandlers) {

		EventStream newEventStream = new EventStream();
		for (AbstractEventHandler storyLandscape : Arrays.asList(abstractEventHandlers)) {
			storyLandscape.setEventStream(newEventStream);
		}

		newEventStream.availableEventHandlers.addAll(Arrays.asList(abstractEventHandlers));
		newEventStream.verificationStrategyFactory = new VerificationStrategyFactory(
				newEventStream.availableEventHandlers);
		return newEventStream;
	}

	private List<AbstractEventHandler<? extends Event>> availableEventHandlers = new ArrayList<AbstractEventHandler<? extends Event>>();

	private ThreadSafeEventStackWorker eventStackWorker = new ThreadSafeEventStackWorker();

	private VerificationStrategyFactory verificationStrategyFactory = new VerificationStrategyFactory(
			availableEventHandlers);
	private RegisterForWaitStrategyFactory registerForWaitStrategyFactory = new RegisterForWaitStrategyFactory(
			availableEventHandlers);

	public <SimulateableEvent extends Event & Simulateable> ThenStream simulate(
			SimulateableEvent simulatePlotableEvent) {

		logRegisterWithStream(RegisterWithStreamType.SIMULATE, simulatePlotableEvent);

		simulatePlotableEvent.getEventOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventStackWorker.add(simulatePlotableEvent);

		findSuitableLandscape(simulatePlotableEvent).simulate(simulatePlotableEvent);

		return new ThenStream(this);

	}

	public <WhenableEvent extends Event & Whenable> OccursThenStream when(WhenableEvent whenableEvent) {

		// need to verify that is complex, all childrens (recursive) are wheanable
		// may be done by whenModule
		// or just make sure they are all of the type of whenable by passing in interface.class within a strategy 
		// i.e recursive#isOfWaitableType(Class<Whenable> i)
		
		logRegisterWithStream(RegisterWithStreamType.WHEN, whenableEvent);

		whenableEvent.getTreeModule().setParentsOfAllChildren(null);

		whenableEvent.getWaitModule().registerForWait(registerForWaitStrategyFactory);

		whenableEvent.getEventOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventStackWorker.add(whenableEvent);

		whenableEvent.getWaitModule().doWait();

		return new OccursThenStream(this);

	}

	public <ExpectableEvent extends Event & Expectable> ThenStream expect(
			ExpectableEvent expectableEvent) {

		//need to verify that is complex, all childrens (recursive) are expectable
		//may be done by expectModule
		
		logRegisterWithStream(RegisterWithStreamType.EXPECT, expectableEvent);

		expectableEvent.getTreeModule().setParentsOfAllChildren(null);

		expectableEvent.getWaitModule().registerForWait(registerForWaitStrategyFactory);

		expectableEvent.getEventOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventStackWorker.add(expectableEvent);

		return new ThenStream(this);
	}

	ThreadSafeEventStackWorker getEventQueueWorker() {
		return eventStackWorker;
	}

	void setEventQueueWorker(ThreadSafeEventStackWorker eventStackWorker) {
		this.eventStackWorker = eventStackWorker;
	}

	private AbstractEventHandler<? extends Event> findSuitableLandscape(Event event) {
		return EventHandlerResolver.findApplicableLandscape(event, availableEventHandlers);
	}

	private void logRegisterWithStream(RegisterWithStreamType registerWithStreamType, Event event) {
		LOGGER.info("<<" + registerWithStreamType.toString() + ">>: " + event.getName());
	}

	public EventStream getStream() {
		return this;

	}
}
