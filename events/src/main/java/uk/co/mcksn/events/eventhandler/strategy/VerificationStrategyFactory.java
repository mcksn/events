package uk.co.mcksn.events.eventhandler.strategy;

import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.type.Verifyable;

public class VerificationStrategyFactory extends AbstractStrategyFactory {

	public VerificationStrategyFactory(List<AbstractEventHandler<? extends Event>> availableEventHandlers) {
		super(availableEventHandlers);
	}

	public VerificationStrategy createVerificationStrategy(Verifyable verifyable)
	{
		return findSuitableLandscape((Event)verifyable).getVerificationStrategy();
	}


}
