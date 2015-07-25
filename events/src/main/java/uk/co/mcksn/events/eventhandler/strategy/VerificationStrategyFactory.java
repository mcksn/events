package uk.co.mcksn.events.eventhandler.strategy;

import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.type.Verifyable;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
@SuppressWarnings("rawtypes")
public class VerificationStrategyFactory extends AbstractStrategyFactory {

	public VerificationStrategyFactory(List<AbstractEventHandler> availableEventHandlers) {
		super(availableEventHandlers);
	}

	public VerificationStrategy createVerificationStrategy(Verifyable verifyable)
	{
		return findSuitableLandscape((Event) verifyable).getVerificationStrategy();
	}


}
