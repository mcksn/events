package uk.co.mcksn.events.event.complex;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.occured.OrEventOccurredModule;

public class OrComplexEvent extends ComplexEvent {

	public OrComplexEvent(Event[] leaves) {
		super();
		this.eventOccuredModule = new OrEventOccurredModule(this);
		this.children.addAll(Arrays.asList(leaves));
	}


}
