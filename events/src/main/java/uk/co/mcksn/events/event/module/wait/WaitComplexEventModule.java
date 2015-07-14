package uk.co.mcksn.events.event.module.wait;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.plot.WaitPlotable;

public class WaitComplexEventModule extends AbstractWaitModule<ComplexEvent> {

	public WaitComplexEventModule(ComplexEvent complexEvent) {
		super(complexEvent);
	}


	public void registerForWait(RegisterForWaitStrategyFactory strategyFactory) {

		for (Event aEvent : waitPlotable.getLeaves()) {

			((WaitPlotable)aEvent).getWaitModule().registerForWait(strategyFactory);
		}
	}
	




}
