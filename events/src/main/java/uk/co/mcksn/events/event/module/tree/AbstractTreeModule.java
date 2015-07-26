package uk.co.mcksn.events.event.module.tree;

import uk.co.mcksn.events.stack.ThreadSafeEventStackWorker;

public abstract class AbstractTreeModule<Treeable> {

	protected Treeable treeable = null;
	protected Treeable parent = null;

	public AbstractTreeModule(Treeable event) {
		super();
		this.treeable = event;
	}

	public Treeable getParent() {
		return parent;
	}

	protected void setParent(Treeable parent) {
		this.parent = parent;
	}

	public abstract void linkTree(Treeable parent);
	
	public abstract void addTreeToStack(ThreadSafeEventStackWorker stackWorker);
	
	public abstract void printTree(int level);
	
	protected void printTreeDecorator(int level) {
		for (int i = 0; i <= level; i++) {
			System.out.print("    |");
		}
	}
}
