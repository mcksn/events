package uk.co.mcksn.events.test.waitable.impl;

import java.util.Collection;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEvent;
import uk.co.mcksn.events.httpincoming.wiremock.WireMockServerDef;
import uk.co.mcksn.events.httpincoming.wiremock.WireMockUtil;
import uk.co.mcksn.events.stack.AbstractUpdateEventsStackWork;
import uk.co.mcksn.events.tree.RecursiveTreeTraverserImpl;
import uk.co.mcksn.events.tree.TreeTraverser;

@SuppressWarnings("rawtypes")
public class TestUtilWaitableUpdateStackWorkImpl extends AbstractUpdateEventsStackWork<HttpInEvent> {

	WireMockServerDef wireMockServerDef = null;
	Request request = null;
	TreeTraverser treeTraverser = new RecursiveTreeTraverserImpl();

	public TestUtilWaitableUpdateStackWorkImpl(WireMockServerDef wireMockServerDef, Request request) {
		this.wireMockServerDef = wireMockServerDef;
		this.request = request;
	}

	protected HttpInEvent matchIncidentToEvent(Collection<Event> events) {
		return WireMockUtil.findMatchingEvent(events, wireMockServerDef, request);
	}

	protected void updateResultModule(HttpInEvent matchedEvent) {
		matchedEvent.getResultModule().addLoggedRequest(LoggedRequest.createFrom(request));

	}

	protected EventState getState(HttpInEvent matchedEvent) {

		if (matchedEvent.getResultModule().getLoggedRequestCount() == matchedEvent.getActionModule().getTimes())
			return EventState.OCCURRED;
		else
			return EventState.IN_PROGRESS;

	}
}
