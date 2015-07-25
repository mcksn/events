package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;

@SuppressWarnings("rawtypes")
public class WaitComplexModule extends AbstractWaitModule<ComplexEvent> {

	public WaitComplexModule(ComplexEvent complexEvent) {
		super(complexEvent);
	}


	public void registerForWait(RegisterForWaitStrategyFactory strategyFactory) {

		for (Event aEvent : waitable.getChildren()) {

			((Waitable)aEvent).getWaitModule().registerForWait(strategyFactory);
		}
	}
	




}
