package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.type.Waitable;

@SuppressWarnings("rawtypes")
public class WaitModule<WaitableEvent extends Event & Waitable> extends AbstractWaitModule<WaitableEvent> {

	public WaitModule(WaitableEvent waitable) {
		super(waitable);
	}

	public void registerForWait(RegisterForWaitStrategyFactory strategyFactory) {
		strategyFactory.createRegisterForWaitStrategy(waitPlotable).registerForWait(waitPlotable);
	}
	
}
