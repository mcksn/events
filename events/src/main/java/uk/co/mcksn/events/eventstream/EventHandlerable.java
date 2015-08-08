package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;

public interface EventHandlerable {

	Class<? extends Event> getEventType();

	VerificationStrategy getVerificationStrategy();

}