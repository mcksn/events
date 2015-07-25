package uk.co.mcksn.events.event.complex;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.type.Waitable;

@SuppressWarnings("rawtypes")
public class WaitComplexEventBuilder {
	
	@SafeVarargs
	public static <WaitableEvent extends Event & Waitable> AndComplexEvent and(WaitableEvent... children) {
		//TODO Check each object to verify safe varargs
		return new AndComplexEvent(children);
	}

	@SafeVarargs
	public static <WaitableEvent extends Event & Waitable> OrComplexEvent or(WaitableEvent... children) {
		//TODO Check each object to verify safe varargs
		return new OrComplexEvent(children);
	}

}
