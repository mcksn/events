package uk.co.mcksn.events.event.complex;

import uk.co.mcksn.events.event.type.Waitable;

@SuppressWarnings("rawtypes")
public class WaitComplexEventBuilder {
	
	public static AndComplexEvent and(Waitable... children) {
		return new AndComplexEvent(children);
	}

	public static OrComplexEvent or(Waitable... children) {
		return new OrComplexEvent(children);
	}

}
