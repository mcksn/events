package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;

public interface WaitHandlerable {
	

	public abstract RegisterForWaitStrategy getRegisterForWaitStrategy();

}
