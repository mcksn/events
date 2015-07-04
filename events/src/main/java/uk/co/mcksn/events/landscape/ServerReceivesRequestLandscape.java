package uk.co.mcksn.events.landscape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestListener;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.verification.VerificationResult;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.event.multi.EventTree;
import uk.co.mcksn.events.exception.VerificationFailedException;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.story.AbstractStoryLandscape;
import uk.co.mcksn.events.story.ThenStory;
import uk.co.mcksn.events.work.SRREventUpdateEventsQueueWorkImpl;

public class ServerReceivesRequestLandscape extends AbstractStoryLandscape<ServerReceivesRequestEvent> {

	private List<WireMockServerDef> registeredWireMockServerDefs = new ArrayList<WireMockServerDef>();

	public static AbstractStoryLandscape<?> looksLike(WireMockServerDef... wireMockServerDefs) {

		ServerReceivesRequestLandscape landscape = new ServerReceivesRequestLandscape();
		// * for(WireMockServerDef wireMockServerDef :
		// Arrays.asList(wireMockServerDefs))
		landscape.registeredWireMockServerDefs.addAll(Arrays.asList(wireMockServerDefs));
		landscape.configureEachWireMockServer();

		return landscape;
	}

	protected void configureEachWireMockServer() {

		for (final WireMockServerDef wireMockServerDef : registeredWireMockServerDefs) {

			wireMockServerDef.getWireMockServer().start();
			wireMockServerDef.getWireMockServer().addMockServiceRequestListener(new RequestListener() {

				public void requestReceived(Request request, Response response) {

					SRREventUpdateEventsQueueWorkImpl updateEventWork = new SRREventUpdateEventsQueueWorkImpl(getEventQueueWorker(), wireMockServerDef,
							request);
					getEventQueueWorker().doWork(updateEventWork);

				}
			});

		}
	}

	protected void when(EventTree event) {

		// todo
	}

	protected void when(Event event) {

		ServerReceivesRequestEvent srrEvent = cast(event);
		srrEvent.getWireMockServerDef().getWireMockServer().addStubMapping(srrEvent.getAction().getStubMapping());

		event.doWait();
	}

	public void verify(Event event) throws VerificationFailedException {

		ServerReceivesRequestEvent srrEvent = cast(event);
		VerificationResult wireMockVerificationResult = srrEvent.getWireMockServerDef().getWireMockServer()
				.countRequestsMatching((srrEvent.getVerificationPolicy().getRequestPattern()));
		if (wireMockVerificationResult.getCount() < 1) {
			if (!srrEvent.getVerificationPolicy().verifyAndContinueStory())
				throw new VerificationFailedException();
		}

	}

	public void simulate(Event event) {
		// todo
	}

	public void expect(Event event) {

		ServerReceivesRequestEvent srrEvent = cast(event);
		srrEvent.getWireMockServerDef().getWireMockServer().addStubMapping(srrEvent.getAction().getStubMapping());

	}

	private static ServerReceivesRequestEvent cast(Event event) {
		if (event instanceof ServerReceivesRequestEvent)
			return (ServerReceivesRequestEvent) event;
		return null;
	}
}
