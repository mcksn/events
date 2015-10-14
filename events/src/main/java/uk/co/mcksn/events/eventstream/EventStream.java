package uk.co.mcksn.events.eventstream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.enumeration.RegisterWithStreamType;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.type.Expectable;
import uk.co.mcksn.events.event.type.Simulateable;
import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.event.type.Whenable;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;
import uk.co.mcksn.events.tree.Treeable;

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
	
	private EventHandlerResolver EVENT_HANDLER_RESOLVER = new EventHandlerResolver();

	/**
	 * Specify the landscapes the story will be set in and start the story
	 * 
	 * @param eventHandlerable
	 * @return
	 */
	public static EventStream given(EventHandlerable ...eventHandlerables) {

		EventStream newEventStream = new EventStream();
		for (EventHandlerable eventHandler : Arrays.asList(eventHandlerables)) {
			eventHandler.setEventStream(newEventStream);
		}

		newEventStream.availableEventHandlers.addAll(Arrays.asList(eventHandlerables));
		newEventStream.verificationStrategyFactory = new VerificationStrategyFactory(
				newEventStream.availableEventHandlers);
		for (EventHandlerable eventHandler : Arrays.asList(eventHandlerables)) {
			eventHandler.startWatching();
		}
		
		return newEventStream;
	}

	private List<EventHandlerable> availableEventHandlers = new ArrayList<EventHandlerable>();

	private ThreadSafeEventStackWorker eventStackWorker = new ThreadSafeEventStackWorker();

	private VerificationStrategyFactory verificationStrategyFactory = new VerificationStrategyFactory(
			availableEventHandlers);
	private RegisterForWaitStrategyFactory registerForWaitStrategyFactory = new RegisterForWaitStrategyFactory(
			availableEventHandlers);

	public ThenStream simulate(Simulateable simulateable) {

		logRegisterWithStream(RegisterWithStreamType.SIMULATE, simulateable);

		simulateable.getOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);
		eventStackWorker.add(simulateable);

		EVENT_HANDLER_RESOLVER.findApplicableHandler(SimulateHandlerable.class, simulateable, availableEventHandlers);

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

		
		waitable.getTreeModule().linkTree(null);

		waitable.getWaitModule().registerForWait(registerForWaitStrategyFactory);

		waitable.getOccurredModule().setVerificationStrategyFactory(verificationStrategyFactory);

		waitable.getTreeModule().addTreeToStack(eventStackWorker);
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
