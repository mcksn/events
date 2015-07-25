package uk.co.mcksn.events.event.module.tree;

public class AbstractTreeModule<Treeable> {

	protected Treeable eventTreeable = null;
	protected Treeable parent = null;

	public AbstractTreeModule(Treeable event) {
		super();
		this.eventTreeable = event;
	}

	public Treeable getParent() {
		return parent;
	}

	public void setParent(Treeable parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(Treeable rootParent) {
		parent = rootParent;
	}
}
