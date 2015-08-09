package uk.co.mcksn.events.test.waitable.impl;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.type.Verifyable;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEvent;

@SuppressWarnings("rawtypes")
public class TestUtilWaitableVerificationStrategy implements VerificationStrategy {

	public VerificationOutcome calculateVerificationOutcome(Verifyable verifyable) {
		if (!verifyable.getOccurredModule().getState().equals(EventState.OCCURRED))
		{
			return VerificationOutcome.UNKOWN;
		}

		return VerificationOutcome.SUCCESS;
	}

}
