package uk.co.mcksn.events.test.waitable.impl;

import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEvent;

@SuppressWarnings("rawtypes")
public class TestUtilWaitableRegisterForWaitStrategy implements RegisterForWaitStrategy {

	public void registerForWait(Waitable waitable) {

		HttpInEvent httpInEvent = cast(waitable);

		httpInEvent.getWireMockServerDef().getWireMockServer().addStubMapping(httpInEvent.getActionModule().getStubMapping());
	}

	private static HttpInEvent cast(Waitable waitable) {
		if (waitable instanceof HttpInEvent) {
			return (HttpInEvent) waitable;
		}
		throw new RuntimeException("Can not cast object to " + HttpInEvent.class.getName());
	}
}
