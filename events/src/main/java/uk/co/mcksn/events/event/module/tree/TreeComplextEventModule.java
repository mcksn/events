package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.tree.EventTreeable;

public class TreeComplextEventModule extends AbstractTreeModule<ComplexEvent> {

	public TreeComplextEventModule(ComplexEvent complexEvent) {
		super(complexEvent);
	}

	public void setParentsOfAllChildren(ComplexEvent rootParent) {

		for (Event aEvent : event.getLeaves()) {

			((EventTreeable) aEvent).getTreeModule().setParentsOfAllChildren(rootParent);
		}
	}
}
