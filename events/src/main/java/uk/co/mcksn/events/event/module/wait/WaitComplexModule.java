package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.type.Waitable;

public class WaitComplexModule extends AbstractWaitModule<ComplexEvent> {

	public WaitComplexModule(ComplexEvent complexEvent) {
		super(complexEvent);
	}


	public void registerForWait(RegisterForWaitStrategyFactory strategyFactory) {

		for (Event aEvent : waitPlotable.getChildren()) {

			((Waitable)aEvent).getWaitModule().registerForWait(strategyFactory);
		}
	}
	




}
