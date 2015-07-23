package uk.co.mcksn.events.incominghttp.wiremock;

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
import uk.co.mcksn.events.stack.SRREventUpdateEventsStackWorkImpl;
import uk.co.mcksn.events.type.Simulateable;

public class ServerReceivesRequestEventHandler extends AbstractEventHandler<ServerReceivesRequestEvent> {

	private List<WireMockServerDef> registeredWireMockServerDefs = new ArrayList<WireMockServerDef>();

	public static AbstractEventHandler<?> looksLike(WireMockServerDef... wireMockServerDefs) {

		ServerReceivesRequestEventHandler landscape = new ServerReceivesRequestEventHandler();
		landscape.registeredWireMockServerDefs.addAll(Arrays.asList(wireMockServerDefs));
		landscape.configureEachWireMockServer();

		return landscape;
	}

	protected void configureEachWireMockServer() {

		for (final WireMockServerDef wireMockServerDef : registeredWireMockServerDefs) {

			wireMockServerDef.getWireMockServer().start();
			wireMockServerDef.getWireMockServer().addMockServiceRequestListener(new RequestListener() {

				public void requestReceived(Request request, Response response) {

					SRREventUpdateEventsStackWorkImpl updateEventWork = new SRREventUpdateEventsStackWorkImpl(wireMockServerDef, request);
					getEventQueueWorker().doWork(updateEventWork);

				}
			});

		}
	}

	@Override
	public VerificationStrategy getVerificationStrategy() {
		return new SRRVerificationStrategy();
	}

	@Override
	protected <SimulatePlotableEvent extends Event & Simulateable> void simulate(
			SimulatePlotableEvent simulatePlotableEvent) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public RegisterForWaitStrategy getRegisterForWaitStrategy() {
		return new SRRRegisterForWaitStrategy();
	}
}