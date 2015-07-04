package uk.co.mcksn.events.work;

import java.util.Collection;

import com.github.tomakehurst.wiremock.http.Request;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ThreadSafeEventQueueWorker;
import uk.co.mcksn.events.event.multi.traverser.EventTreeTraverser;
import uk.co.mcksn.events.event.multi.traverser.RecursiveEventTraverserImpl;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.server.WireMockUtil;

public class SRREventUpdateEventsQueueWorkImpl extends AbstractUpdateEventsQueueWork {

	ThreadSafeEventQueueWorker threadSafeEventQueueWorker = null;
	WireMockServerDef wireMockServerDef = null;
	Request request = null;
	EventTreeTraverser eventTreeTraverser = new RecursiveEventTraverserImpl();

	public SRREventUpdateEventsQueueWorkImpl(ThreadSafeEventQueueWorker threadSafeEventQueueWorker,
			WireMockServerDef wireMockServerDef, Request request) {
		super(threadSafeEventQueueWorker);
		this.wireMockServerDef = wireMockServerDef;
		this.request = request;
	}

	protected Event matchWorkToEvent(Collection<Event> events) {
		Event event = WireMockUtil.findMatchingEvent(events, wireMockServerDef, request);
		if (event == null) {
			System.err.println("Event was not found during matching");
		} else {
			event.setState(EventState.OCCURRED);
		}
		return event;
	}

}
