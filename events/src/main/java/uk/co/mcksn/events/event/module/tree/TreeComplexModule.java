package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public class TreeComplexModule extends AbstractTreeModule<ComplexEvent> {

	public TreeComplexModule(ComplexEvent complexEvent) {
		super(complexEvent);
	}

	@SuppressWarnings("unchecked")
	public void setParentsOfAllChildren(ComplexEvent rootParent) {

		for (Event aEvent : eventTreeable.getChildren()) {

			((Treeable) aEvent).getTreeModule().setParentsOfAllChildren(rootParent);
		}
	}
}
