package uk.co.mcksn.events.event.strategy;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;

public class SRRRegisterForWaitStrategy implements RegisterForWaitStrategy {

	@Override
	public void registerForWait(Event event) {

		ServerReceivesRequestEvent srrEvent = cast(event);

		srrEvent.getWireMockServerDef().getWireMockServer().addStubMapping(srrEvent.getAction().getStubMapping());
	}

	private static ServerReceivesRequestEvent cast(Event event) {
		if (event instanceof ServerReceivesRequestEvent) {
			return (ServerReceivesRequestEvent) event;
		}
		throw new RuntimeException("Can not cast object to " + ServerReceivesRequestEvent.class.getName());
	}
}
