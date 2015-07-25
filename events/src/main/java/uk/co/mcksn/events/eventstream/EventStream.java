package uk.co.mcksn.events.eventstream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.enumeration.RegisterWithStreamType;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.type.Expectable;
import uk.co.mcksn.events.event.type.Simulateable;
import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.event.type.Whenable;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;

/**
 * A story is a sequence of {@link Event}s that will perform a testable
 * scenario.
 * 
 * @author mackson
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EventStream {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventStream.class);

	/**
	 * Specify the landscapes the story will be set in and start the story
	 * 
	 * @param abstractEventHandlers
	 * @return
	 */
	public static EventStream given(AbstractEventHandler abstractEventHandlers) {

		EventStream newEventStream = new EventStream();
		for (AbstractEventHandler eventHandler : Arrays.asList(abstractEventHandlers)) {
			eventHandler.setEventStream(newEventStream);
		}

		newEventStream.availableEventHandlers.addAll(Arrays.asList(abstractEventHandlers));
		newEventStream.verificationStrategyFactory = new VerificationStrategyFactory(
				newEventStream.availableEventHandlers);
		return newEventStream;
	}

	private List<AbstractEventHandler> availableEventHandlers = new ArrayList<AbstractEventHandler>();

	private ThreadSafeEventStackWorker eventStackWorker = new ThreadSafeEventStackWorker();

	private VerificationStrategyFactory verificationStrategyFactory = new VerificationStrategyFactory(
			availableEventHandlers);
	private RegisterForWaitStrategyFactory registerForWaitStrategyFactory = new RegisterForWaitStrategyFactory(
			availableEventHandlers);

	public ThenStream simulate(Simulateable simulateable) {

		logRegisterWithStream(RegisterWithStreamType.SIMULATE, simulateable);

		simulateable.getOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventStackWorker.add(simulateable);

		EventHandlerResolver.findApplicableHandler(SimulateHandlerable.class, simulateable, availableEventHandlers);

		return new ThenStream(this);

	}

	public OccursThenStream when(Whenable whenable) {

		logRegisterWithStream(RegisterWithStreamType.WHEN, whenable);

		internalWait(whenable);

		whenable.getWaitModule().doWait();

		return new OccursThenStream(this);

	}

	public ThenStream expect(Expectable expectable) {

		logRegisterWithStream(RegisterWithStreamType.EXPECT, expectable);

		internalWait(expectable);

		return new ThenStream(this);
	}

	public void internalWait(Waitable waitable) {

		waitable.getTreeModule().setParentsOfAllChildren(null);

		waitable.getWaitModule().registerForWait(registerForWaitStrategyFactory);

		waitable.getOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventStackWorker.add(waitable);

	}

	ThreadSafeEventStackWorker getEventQueueWorker() {
		return eventStackWorker;
	}

	void setEventQueueWorker(ThreadSafeEventStackWorker eventStackWorker) {
		this.eventStackWorker = eventStackWorker;
	}

	private void logRegisterWithStream(RegisterWithStreamType registerWithStreamType, Event event) {
		LOGGER.info("<<" + registerWithStreamType.toString() + ">>: " + event.getName());
	}

	public EventStream getStream() {
		return this;

	}
}
