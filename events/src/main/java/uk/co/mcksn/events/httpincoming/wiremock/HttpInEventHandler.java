package uk.co.mcksn.events.httpincoming.wiremock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestListener;
import com.github.tomakehurst.wiremock.http.Response;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.WaitHandlerable;
import uk.co.mcksn.events.stack.HttpInEventUpdateStackWorkImpl;

@SuppressWarnings("rawtypes")
public class HttpInEventHandler extends AbstractEventHandler implements WaitHandlerable {

	private List<WireMockServerDef> registeredWireMockServerDefs = new ArrayList<WireMockServerDef>();

	public static AbstractEventHandler looksLike(WireMockServerDef... wireMockServerDefs) {

		HttpInEventHandler eventHandler = new HttpInEventHandler();
		eventHandler.registeredWireMockServerDefs.addAll(Arrays.asList(wireMockServerDefs));
		eventHandler.configureEachWireMockServer();

		return eventHandler;
	}
	
	public void startWatching()
	{
		configureEachWireMockServer();
	}

	protected void configureEachWireMockServer() {

		for (final WireMockServerDef wireMockServerDef : registeredWireMockServerDefs) {

			wireMockServerDef.getWireMockServer().start();
			wireMockServerDef.getWireMockServer().addMockServiceRequestListener(new RequestListener() {

				public void requestReceived(Request request, Response response) {

					HttpInEventUpdateStackWorkImpl updateEventWork = new HttpInEventUpdateStackWorkImpl(
							wireMockServerDef, request);
					getEventQueueWorker().doWork(updateEventWork);

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
