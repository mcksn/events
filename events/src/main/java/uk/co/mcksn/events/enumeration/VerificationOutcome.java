package uk.co.mcksn.events.enumeration;

import java.util.Arrays;
import java.util.Collection;

public enum VerificationOutcome {
	UNKOWN(1), FAULURE(2), SUCCESS(0);

	private int precedence;

	private VerificationOutcome(int precedence) {
		this.precedence = precedence;
	}

	/**
	 * TODO Unit test
	 * 
	 * @param verificationOutcomes
	 * @return
	 */
	public static VerificationOutcome getOverallVerificationOutcome(
			Collection<VerificationOutcome> verificationOutcomes) {
		VerificationOutcome outcomeSoFar = SUCCESS;

		for (VerificationOutcome currentOutcome : verificationOutcomes) {
			if (currentOutcome.precedence > outcomeSoFar.precedence)
				outcomeSoFar = currentOutcome;
		}

		return outcomeSoFar;
	}

}
