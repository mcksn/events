package uk.co.mcksn.events.stack;

import java.util.Collection;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.incominghttp.wiremock.ServerReceivesRequestEvent;
import uk.co.mcksn.events.incominghttp.wiremock.WireMockServerDef;
import uk.co.mcksn.events.incominghttp.wiremock.WireMockUtil;
import uk.co.mcksn.events.tree.RecursiveTreeTraverserImpl;
import uk.co.mcksn.events.tree.TreeTraverser;

@SuppressWarnings("rawtypes")
public class SRREventUpdateEventsStackWorkImpl extends AbstractUpdateEventsStackWork<ServerReceivesRequestEvent> {

	WireMockServerDef wireMockServerDef = null;
	Request request = null;
	TreeTraverser treeTraverser = new RecursiveTreeTraverserImpl();

	public SRREventUpdateEventsStackWorkImpl(WireMockServerDef wireMockServerDef, Request request) {
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
