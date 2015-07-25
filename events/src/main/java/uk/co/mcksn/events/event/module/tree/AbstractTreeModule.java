package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.tree.Treeable;


@SuppressWarnings("rawtypes")
public class AbstractTreeModule<TreeableEvent extends Event & Treeable> {

	protected TreeableEvent eventTreeable = null;
	protected TreeableEvent parent = null;

	public AbstractTreeModule(TreeableEvent event) {
		super();
		this.eventTreeable = event;
	}

	public TreeableEvent getParent() {
		return parent;
	}

	public void setParent(TreeableEvent parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(TreeableEvent rootParent) {
		parent = rootParent;
	}
}
