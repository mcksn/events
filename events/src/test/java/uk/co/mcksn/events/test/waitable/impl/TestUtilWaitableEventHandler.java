package uk.co.mcksn.events.test.waitable.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.EventHandlerable;
import uk.co.mcksn.events.eventstream.WaitHandlerable;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEvent;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInRegisterForWaitStrategy;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInVerificationStrategy;

@SuppressWarnings("rawtypes")
public class TestUtilWaitableEventHandler extends AbstractEventHandler implements WaitHandlerable {

	public static EventHandlerable looksLike(AtomicInteger... watchableObjects) {

		TestUtilWaitableEventHandler eventHandler = new TestUtilWaitableEventHandler();
		eventHandler.watchableObjects.addAll(Arrays.asList(watchableObjects));

		return eventHandler;
	}

	private List<AtomicInteger> watchableObjects = new ArrayList<AtomicInteger>();

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	public void startWatching() {
		configureWatchForEachWatchable();
	}

	protected void configureWatchForEachWatchable() {

		for (final AtomicInteger watchableObject : watchableObjects) {

			configureWatchForAWatchable(watchableObject);
		}
	}

	protected void configureWatchForAWatchable(final AtomicInteger watchableObj) {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					TestUtilWaitableUpdateStackWorkImpl updateEventWork = new TestUtilWaitableUpdateStackWorkImpl(
							watchableObj.get());
					getEventQueueWorker().doWork(updateEventWork);
				}

			}
		});
	}

	@Override
	public VerificationStrategy getVerificationStrategy() {
		return new TestUtilWaitableVerificationStrategy();
	}

	@Override
	public RegisterForWaitStrategy getRegisterForWaitStrategy() {
		return new TestUtilWaitableRegisterForWaitStrategy();
	}

	@Override
	public Class<? extends Event> getEventType() {
		return TestUtilWaitableEvent.class;
	}

	public void cleanUp() {
		executorService.shutdownNow();
	}
}
