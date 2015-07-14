package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.plot.WaitPlotable;

public class AbstractTreeModule<E extends Event> {

	protected E event = null;
	protected Event parent = null;

	public AbstractTreeModule(E event) {
		super();
		this.event = event;
	}

	public Event getParent() {
		return parent;
	}

	public void setParent(ComplexEvent parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(ComplexEvent rootParent) {
		parent = rootParent;
	}
}
