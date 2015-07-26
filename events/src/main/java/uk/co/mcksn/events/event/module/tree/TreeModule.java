package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public class TreeModule extends AbstractTreeModule<Treeable> {

	public TreeModule(Treeable treeable) {
		super(treeable);
	}

	public void linkTree(Treeable parent) {
		setParent(parent);
	}

	public void addTreeToStack(ThreadSafeEventStackWorker stackWorker) {
		stackWorker.add(treeable);
	}
	
	@Override
	public void printTree(int level) {
		printTreeDecorator(level);

		System.out.println(treeable.getName());
	}
}
