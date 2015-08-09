package uk.co.mcksn.events.test.waitable.impl;

import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventhandler.strategy.RegisterForWaitStrategy;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEvent;

@SuppressWarnings("rawtypes")
public class TestUtilWaitableRegisterForWaitStrategy implements RegisterForWaitStrategy {

	public void registerForWait(Waitable waitable) {
		// do nothing
	}
}
