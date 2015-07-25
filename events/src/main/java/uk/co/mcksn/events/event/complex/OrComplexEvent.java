package uk.co.mcksn.events.event.complex;

import java.util.Arrays;

import uk.co.mcksn.events.event.module.occured.OrOccurredModule;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings({ "rawtypes"})
public class OrComplexEvent extends ComplexEvent {

	public OrComplexEvent(Treeable... children) {
		super();
		this.eventOccuredModule = new OrOccurredModule(this);
		
		this.addChildren(Arrays.asList(children));
	}

}
