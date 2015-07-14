package uk.co.mcksn.events.landscape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestListener;
import com.github.tomakehurst.wiremock.http.Response;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.event.strategy.SRRVerificationStrategy;
import uk.co.mcksn.events.event.strategy.VerificationStrategy;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.exception.VerificationNtSuccessfulException;
import uk.co.mcksn.events.plot.WhenPlotable;
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

					SRREventUpdateEventsQueueWorkImpl updateEventWork = new SRREventUpdateEventsQueueWorkImpl(
							getEventQueueWorker(), wireMockServerDef, request);
					getEventQueueWorker().doWork(updateEventWork);

				}
			});

		}
	}

	protected void when(EventTreeable event) {

		// todo
	}

	protected void registerWaitPlotableEvent(Event event) {

		ServerReceivesRequestEvent srrEvent = cast(event);
		srrEvent.getWireMockServerDef().getWireMockServer().addStubMapping(srrEvent.getAction().getStubMapping());


	}

	public void simulate(Event event) {
		// todo
	}

	public void expect(Event event) {

		ServerReceivesRequestEvent srrEvent = cast(event);
		srrEvent.getWireMockServerDef().getWireMockServer().addStubMapping(srrEvent.getAction().getStubMapping());

	}

	private static ServerReceivesRequestEvent cast(Event event) {
		if (event instanceof ServerReceivesRequestEvent) {
			return (ServerReceivesRequestEvent) event;
		}
		throw new RuntimeException("Can not cast object to " + ServerReceivesRequestEvent.class.getName());
	}

	@Override
	public VerificationStrategy getVerificationStrategy() {
		return new SRRVerificationStrategy();
	}
}
