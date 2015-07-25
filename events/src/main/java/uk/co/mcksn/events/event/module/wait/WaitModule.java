package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;

@SuppressWarnings("rawtypes")
public class WaitModule extends AbstractWaitModule<Waitable> {

	public WaitModule(Waitable waitable) {
		super(waitable);
	}

	public void registerForWait(RegisterForWaitStrategyFactory strategyFactory) {
		strategyFactory.createRegisterForWaitStrategy(waitable).registerForWait(waitable);
	}
	
}
