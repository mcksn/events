package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.tree.EventTreeable;

public class AbstractTreeModule<E extends EventTreeable> {

	protected E eventTreeable = null;
	protected E parent = null;

	public AbstractTreeModule(E event) {
		super();
		this.eventTreeable = event;
	}

	public E getParent() {
		return parent;
	}

	public void setParent(E parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(E rootParent) {
		parent = rootParent;
	}
}
