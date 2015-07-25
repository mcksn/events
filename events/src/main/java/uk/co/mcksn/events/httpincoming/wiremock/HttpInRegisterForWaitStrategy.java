package uk.co.mcksn.events.httpincoming.wiremock;

import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;

@SuppressWarnings("rawtypes")
public class HttpInRegisterForWaitStrategy implements RegisterForWaitStrategy {

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
