package uk.co.mcksn.events.httpincoming.wiremock;

import static com.google.common.collect.Iterables.find;

import java.util.Collection;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.common.base.Predicate;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.Event;

public class WireMockUtil {

	public static HttpInEvent findMatchingEvent(Collection<Event> events,
			WireMockServerDef wireMockServerDef, Request request) {

		Event matchingEvent = find(events, mappingMatchingAndInCorrectScenarioState(request), null);

		if (!((HttpInEvent) matchingEvent).getWireMockServerDef().equals(wireMockServerDef)) {
			matchingEvent = null;
		}

		return (HttpInEvent) matchingEvent;

	}

	private static Predicate<Event> mappingMatchingAndInCorrectScenarioState(final Request request) {
		return new Predicate<Event>() {
			public boolean apply(Event event) {
				if (event instanceof HttpInEvent) {
					HttpInEvent httpInEvent = (HttpInEvent) event;
					StubMapping httpInEventStubMapping = httpInEvent.getActionModule().getStubMapping();
					return httpInEventStubMapping.getRequest().isMatchedBy(request)
							&& (httpInEventStubMapping.isIndependentOfScenarioState()
									|| httpInEventStubMapping.requiresCurrentScenarioState())
							&& !event.getOccurredModule().getState().equals(EventState.OCCURRED);
				} else
					return false;
			}

		};

	}
}
