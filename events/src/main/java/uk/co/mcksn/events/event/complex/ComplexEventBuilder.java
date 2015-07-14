package uk.co.mcksn.events.event.complex;

import uk.co.mcksn.events.event.Event;

public class ComplexEventBuilder {

	public static AndComplexEvent and(Event... leaves) {
		return new AndComplexEvent(leaves);
	}

	public static OrComplexEvent or(Event... leaves) {
		return new OrComplexEvent(leaves);
	}

}
