package uk.co.mcksn.events.event.complex;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.occured.AndOccurredModule;
import uk.co.mcksn.events.event.module.wait.WaitComplexModule;

public class AndComplexEvent extends ComplexEvent {

	public AndComplexEvent(Event[] leaves) {
		super();
		this.eventOccuredModule = new AndOccurredModule(this);
		this.children.addAll(Arrays.asList(leaves));
	}

}
