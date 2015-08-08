package uk.co.mcksn.events.test.waitable.impl;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

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

	private List<AtomicInteger> watchableObjects = new ArrayList<AtomicInteger>();

	public static EventHandlerable looksLike(AtomicInteger... watchableObjects) {

		TestUtilWaitableEventHandler eventHandler = new TestUtilWaitableEventHandler();
		eventHandler.watchableObjects.addAll(Arrays.asList(watchableObjects));

		return eventHandler;
	}

	protected void configureEachWireMockServer() {

		for (final AtomicInteger watchableObject : watchableObjects) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					Date beforeLoopDate = new Date();
					// Date expectedLoopEndDate = new Date(beforeLoopDate).;
					for (;;) {
						try {
							Thread.sleep(100L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						TestUtilWaitableUpdateStackWorkImpl updateEventWork = new TestUtilWaitableUpdateStackWorkImpl(
								watchableObject.get());
						getEventQueueWorker().doWork(updateEventWork);
						// if (beforeLoopDate.before(new Date())
					}

				}
			});
		}
	}

	@Override
	public VerificationStrategy getVerificationStrategy() {
		return new HttpInVerificationStrategy();
	}

	@Override
	public RegisterForWaitStrategy getRegisterForWaitStrategy() {
		return new HttpInRegisterForWaitStrategy();
	}

	@Override
	public Class<? extends Event> getEventType() {
		return HttpInEvent.class;
	}
}
