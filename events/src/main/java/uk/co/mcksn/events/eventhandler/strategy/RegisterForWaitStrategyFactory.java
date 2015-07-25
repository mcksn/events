package uk.co.mcksn.events.eventhandler.strategy;

import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.WaitHandlerable;

@SuppressWarnings("rawtypes")
public class RegisterForWaitStrategyFactory extends AbstractStrategyFactory {

	public RegisterForWaitStrategyFactory(List<AbstractEventHandler> availableEventHandlers) {
		super(availableEventHandlers);
	}

	public RegisterForWaitStrategy createRegisterForWaitStrategy(Waitable waitable) {
		return findSuitableLandscape(WaitHandlerable.class, (Event) waitable).getRegisterForWaitStrategy();
	}

}
