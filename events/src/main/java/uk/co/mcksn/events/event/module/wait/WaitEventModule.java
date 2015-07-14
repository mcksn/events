package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.event.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.plot.WaitPlotable;

public class WaitEventModule extends AbstractWaitModule<WaitPlotable> {

	public WaitEventModule(WaitPlotable event) {
		super(event);
	}

	public void registerForWait(RegisterForWaitStrategyFactory strategyFactory) {
		((WaitPlotable)waitPlotable).getWaitModule().registerForWait(strategyFactory);
	}
	
}
