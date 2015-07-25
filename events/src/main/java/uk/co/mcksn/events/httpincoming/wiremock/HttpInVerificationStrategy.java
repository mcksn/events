package uk.co.mcksn.events.httpincoming.wiremock;

import java.util.Iterator;

import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.type.Verifyable;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategy;

@SuppressWarnings("rawtypes")
public class HttpInVerificationStrategy implements VerificationStrategy {

	public VerificationOutcome calculateVerificationOutcome(Verifyable verifyable) {
		if (!verifyable.getOccurredModule().getState().equals(EventState.OCCURRED))
			return VerificationOutcome.UNKOWN;

		HttpInEvent httpInEvent = cast(verifyable);

		boolean success = verifyRequestPatternInPolicyMatchAllLoggedRequests(
				httpInEvent.getResultModule().getLoggedRequestIterator(),
				httpInEvent.getVerificationPolicyModule().getRequestPattern());

		if (success)
			return VerificationOutcome.SUCCESS;
		else
			return VerificationOutcome.FAULURE;
	}

	private static HttpInEvent cast(Verifyable verifyable) {
		if (verifyable instanceof HttpInEvent) {
			return (HttpInEvent) verifyable;
		}
		throw new RuntimeException("Can not cast object to " + HttpInEvent.class.getName());
	}

	/**
	 * TODO Explain what is happening here
	 * 
	 * @param iterator
	 * @param requestPattern
	 * @return
	 */
	boolean verifyRequestPatternInPolicyMatchAllLoggedRequests(Iterator<LoggedRequest> iterator,
			RequestPattern requestPattern) {
		boolean success = true;
		boolean empty = true; // iterator has no is empty

		while (iterator.hasNext()) {
			empty = false;

			if (!requestPattern.isMatchedBy(iterator.next())) {
				return false;
			}
		}

		if (empty) {
			return false;
		}
		return success;

	}
}
