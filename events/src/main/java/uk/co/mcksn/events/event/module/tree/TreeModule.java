package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public class TreeModule<TreeableEvent extends Event & Treeable> extends AbstractTreeModule<TreeableEvent> {

	public TreeModule(TreeableEvent treeable) {
		super(treeable);
	}

	public void setParentsOfAllChildren(TreeableEvent rootParent) {
		parent = rootParent;
	}
}
