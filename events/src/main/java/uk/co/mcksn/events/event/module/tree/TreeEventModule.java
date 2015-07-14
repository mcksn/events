package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.plot.WaitPlotable;

public class TreeEventModule extends AbstractTreeModule<Event> {

	public TreeEventModule(Event event) {
		super(event);
	}

	public void setParentsOfAllChildren(ComplexEvent rootParent) {
		parent = rootParent;
	}
}
