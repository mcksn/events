package uk.co.mcksn.events.test.waitable.impl;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.WaitHandlerable;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEvent;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInRegisterForWaitStrategy;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInVerificationStrategy;

@SuppressWarnings("rawtypes")
public class TestUtilWaitableEventHandler extends AbstractEventHandler implements WaitHandlerable {

	private List<Path> testPaths = new ArrayList<Path>();

	public static AbstractEventHandler looksLike(Path... testPaths) {

		TestUtilWaitableEventHandler eventHandler = new TestUtilWaitableEventHandler();
		eventHandler.testPaths.addAll(Arrays.asList(testPaths));
		eventHandler.configureEachWireMockServer();

		return eventHandler;
	}

	protected void configureEachWireMockServer() {

		
		final WatchService watchService = FileSystems.getDefault().newWatchService();
		for (Path testPath : testPaths) {
			
			testPath.register(
			        watchService,
			        StandardWatchEventKinds.ENTRY_MODIFY);
			new Thread(new Runnable() {

				@Override
				public void run() {
					for(;;){
					    WatchKey key = watchService.take();

					    //Poll all the events queued for the key
					    for ( WatchEvent<?> event: key.pollEvents()){
					    	TestUtilWaitableUpdateStackWorkImpl updateEventWork = new TestUtilWaitableUpdateStackWorkImpl(key,testPath.getFileName());
							getEventQueueWorker().doWork(updateEventWork);
					        }
					    }
					    //reset is invoked to put the key back to ready state
					    boolean valid = key.reset();

					    //If the key is invalid, just exit.
					    if ( !valid){
					        break;
					    }
					}

				}
			});

	public void requestReceived(Request request, Response response) {

	}

	});

	}}

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
