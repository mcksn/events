package uk.co.mcksn.events.work;

import java.util.Collection;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.event.multi.traverser.EventTreeTraverser;
import uk.co.mcksn.events.event.multi.traverser.RecursiveEventTraverserImpl;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.server.WireMockUtil;

public class SRREventUpdateEventsQueueWorkImpl extends AbstractUpdateEventsQueueWork<ServerReceivesRequestEvent> {

	WireMockServerDef wireMockServerDef = null;
	Request request = null;
	EventTreeTraverser eventTreeTraverser = new RecursiveEventTraverserImpl();

	public SRREventUpdateEventsQueueWorkImpl(WireMockServerDef wireMockServerDef, Request request) {
		this.wireMockServerDef = wireMockServerDef;
		this.request = request;
	}

	protected ServerReceivesRequestEvent matchWorkToEvent(Collection<Event> events) {
		return WireMockUtil.findMatchingEvent(events, wireMockServerDef, request);
	}

	protected void updateResultModule(ServerReceivesRequestEvent matchedEvent) {
		matchedEvent.getResultModule().setLoggedRequest(LoggedRequest.createFrom(request));
	}


}
