package uk.co.mcksn.events.event.complex;

import java.util.Arrays;

import uk.co.mcksn.events.event.module.occured.AndOccurredModule;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings({ "rawtypes"})
public class AndComplexEvent extends ComplexEvent {

	public AndComplexEvent(Treeable... leaves) {
		super();
		this.eventOccuredModule = new AndOccurredModule(this);
	
		this.addChildren(Arrays.asList(leaves));
	}

}
