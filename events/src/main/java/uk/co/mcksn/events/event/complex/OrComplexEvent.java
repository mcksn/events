package uk.co.mcksn.events.event.complex;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.occured.OrOccurredModule;

@SuppressWarnings("rawtypes")
public class OrComplexEvent extends ComplexEvent {

	public OrComplexEvent(Event[] leaves) {
		super();
		this.eventOccuredModule = new OrOccurredModule(this);
		this.children.addAll(Arrays.asList(leaves));
	}

}
