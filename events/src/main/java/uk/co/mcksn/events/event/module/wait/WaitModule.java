package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.type.Waitable;

public class WaitModule extends AbstractWaitModule<Waitable> {

	public WaitModule(Waitable waitable) {
		super(waitable);
	}

	public void registerForWait(RegisterForWaitStrategyFactory strategyFactory) {
		strategyFactory.createRegisterForWaitStrategy(waitPlotable).registerForWait(waitPlotable);
	}
	
}
