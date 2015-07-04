package uk.co.mcksn.events.server;

import static com.google.common.collect.Iterables.find;

import java.util.Collection;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.common.base.Predicate;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;

public class WireMockUtil {

	public static Event findMatchingEvent(Collection<Event> events, WireMockServerDef wireMockServerDef,
			Request request) {

		Event matchingEvent = find(events, mappingMatchingAndInCorrectScenarioState(request), null);

		if (!((ServerReceivesRequestEvent) matchingEvent).getWireMockServerDef().equals(wireMockServerDef)) {
			matchingEvent = null;
		}

		return matchingEvent;

	}

	private static Predicate<Event> mappingMatchingAndInCorrectScenarioState(final Request request) {
		return new Predicate<Event>() {
			public boolean apply(Event event) {
				if (event instanceof ServerReceivesRequestEvent) {
					ServerReceivesRequestEvent srrEvent = (ServerReceivesRequestEvent) event;
					StubMapping srrEventStubMapping = srrEvent.getAction().getStubMapping();
					return srrEventStubMapping.getRequest().isMatchedBy(request)
							&& (srrEventStubMapping.isIndependentOfScenarioState()
									|| srrEventStubMapping.requiresCurrentScenarioState())
							&& !event.getState().equals(EventState.OCCURRED);
				} else
					return false;
			}

		};

	}
}
