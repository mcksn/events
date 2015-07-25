package uk.co.mcksn.events.eventhandler.strategy;

import uk.co.mcksn.events.event.type.Waitable;

@SuppressWarnings("rawtypes")
public interface RegisterForWaitStrategy {

	void registerForWait(Waitable waitable);

}
