package uk.co.mcksn.events.landscape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestListener;
import com.github.tomakehurst.wiremock.http.Response;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.event.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.event.strategy.SRRRegisterForWaitStrategy;
import uk.co.mcksn.events.event.strategy.SRRVerificationStrategy;
import uk.co.mcksn.events.event.strategy.VerificationStrategy;
import uk.co.mcksn.events.plot.SimulatePlotable;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.story.AbstractStoryLandscape;
import uk.co.mcksn.events.work.SRREventUpdateEventsQueueWorkImpl;

public class ServerReceivesRequestLandscape extends AbstractStoryLandscape<ServerReceivesRequestEvent> {

	private List<WireMockServerDef> registeredWireMockServerDefs = new ArrayList<WireMockServerDef>();

	public static AbstractStoryLandscape<?> looksLike(WireMockServerDef... wireMockServerDefs) {

		ServerReceivesRequestLandscape landscape = new ServerReceivesRequestLandscape();
		landscape.registeredWireMockServerDefs.addAll(Arrays.asList(wireMockServerDefs));
		landscape.configureEachWireMockServer();

		return landscape;
	}

	protected void configureEachWireMockServer() {

		for (final WireMockServerDef wireMockServerDef : registeredWireMockServerDefs) {

			wireMockServerDef.getWireMockServer().start();
			wireMockServerDef.getWireMockServer().addMockServiceRequestListener(new RequestListener() {

				public void requestReceived(Request request, Response response) {

					SRREventUpdateEventsQueueWorkImpl updateEventWork = new SRREventUpdateEventsQueueWorkImpl(wireMockServerDef, request);
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
	protected <SimulatePlotableEvent extends Event & SimulatePlotable> void simulate(
			SimulatePlotableEvent simulatePlotableEvent) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public RegisterForWaitStrategy getRegisterForWaitStrategy() {
		return new SRRRegisterForWaitStrategy();
	}
}
