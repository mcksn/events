package uk.co.mcksn.events.eventhandler.strategy;

import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.type.Waitable;

public class RegisterForWaitStrategyFactory extends AbstractStrategyFactory {

	public RegisterForWaitStrategyFactory(List<AbstractEventHandler<? extends Event>> availableEventHandlers) {
		super(availableEventHandlers);
	}

	public RegisterForWaitStrategy createRegisterForWaitStrategy(Waitable waitable) {
		return findSuitableLandscape((Event) waitable).getRegisterForWaitStrategy();
	}

}
