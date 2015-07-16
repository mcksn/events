package uk.co.mcksn.events.event.strategy;

import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.plot.WaitPlotable;

public class SRRRegisterForWaitStrategy implements RegisterForWaitStrategy {

	@Override
	public void registerForWait(WaitPlotable waitPlotable) {

		ServerReceivesRequestEvent srrEvent = cast(waitPlotable);

		srrEvent.getWireMockServerDef().getWireMockServer().addStubMapping(srrEvent.getActionModule().getStubMapping());
	}

	private static ServerReceivesRequestEvent cast(WaitPlotable waitPlotable) {
		if (waitPlotable instanceof ServerReceivesRequestEvent) {
			return (ServerReceivesRequestEvent) waitPlotable;
		}
		throw new RuntimeException("Can not cast object to " + ServerReceivesRequestEvent.class.getName());
	}
}
