package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public class TreeModule extends AbstractTreeModule<Treeable> {

	public TreeModule(Treeable treeable) {
		super(treeable);
	}

	public void setParentsOfAllChildren(Treeable rootParent) {
		parent = rootParent;
	}
}
