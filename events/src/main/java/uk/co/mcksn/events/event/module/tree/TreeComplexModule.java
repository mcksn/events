package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public class TreeComplexModule extends AbstractTreeModule<ComplexEvent> {

	public TreeComplexModule(ComplexEvent complexEvent) {
		super(complexEvent);
	}

	@SuppressWarnings("unchecked")
	public void linkTree(ComplexEvent parent) {
		setParent(parent);
		for (Treeable aTreeable : treeable.getChildren()) {

			aTreeable.getTreeModule().linkTree(treeable);
		}
	}

	public void addTreeToStack(ThreadSafeEventStackWorker stackWorker) {
		stackWorker.add(treeable);
		for (Treeable aTreeable : treeable.getChildren()) {

			aTreeable.getTreeModule().addTreeToStack(stackWorker);
		}
	}

	@Override
	public void printTree(int level) {
		printTreeDecorator(level);

		System.out.println(treeable.getName());

		for (Treeable aTreeable : treeable.getChildren()) {

			aTreeable.getTreeModule().printTree(level + 1);
		}

	}
}
