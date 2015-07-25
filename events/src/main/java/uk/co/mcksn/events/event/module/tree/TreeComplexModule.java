package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public class TreeComplexModule extends AbstractTreeModule<ComplexEvent> {

	public TreeComplexModule(ComplexEvent complexEvent) {
		super(complexEvent);
	}

	@SuppressWarnings("unchecked")
	public void setParentsOfAllChildren(ComplexEvent rootParent) {

		for (Treeable aTreeable : eventTreeable.getChildren()) {

			aTreeable.getTreeModule().setParentsOfAllChildren(rootParent);
		}
	}
}
