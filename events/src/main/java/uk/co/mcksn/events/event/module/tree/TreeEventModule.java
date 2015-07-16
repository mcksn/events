package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.tree.EventTreeable;

public class TreeEventModule extends AbstractTreeModule<EventTreeable> {

	public TreeEventModule(EventTreeable eventTreeable) {
		super(eventTreeable);
	}

	public void setParentsOfAllChildren(EventTreeable rootParent) {
		parent = rootParent;
	}
}
