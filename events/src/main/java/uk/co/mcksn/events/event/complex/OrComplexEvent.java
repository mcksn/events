package uk.co.mcksn.events.event.complex;

import java.util.Arrays;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.occured.OrOccurredModule;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrComplexEvent extends ComplexEvent {

	public <TreeableEvent extends Event & Treeable>  OrComplexEvent(TreeableEvent... children) {
		super();
		this.eventOccuredModule = new OrOccurredModule(this);
		
		this.addChildren(Arrays.asList(children));
	}

}
