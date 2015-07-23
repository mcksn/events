package uk.co.mcksn.events.incominghttp.wiremock;

import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.type.Waitable;

public class SRRRegisterForWaitStrategy implements RegisterForWaitStrategy {

	@Override
	public void registerForWait(Waitable waitable) {

		ServerReceivesRequestEvent srrEvent = cast(waitable);

		srrEvent.getWireMockServerDef().getWireMockServer().addStubMapping(srrEvent.getActionModule().getStubMapping());
	}

	private static ServerReceivesRequestEvent cast(Waitable waitable) {
		if (waitable instanceof ServerReceivesRequestEvent) {
			return (ServerReceivesRequestEvent) waitable;
		}
		throw new RuntimeException("Can not cast object to " + ServerReceivesRequestEvent.class.getName());
	}
}
