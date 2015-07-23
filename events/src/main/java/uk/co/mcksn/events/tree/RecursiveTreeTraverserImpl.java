package uk.co.mcksn.events.tree;

public class RecursiveTreeTraverserImpl implements TreeTraverser {


	public <T extends Treeable> T  getRootEventTreeable(T eventTreeable) {

		return internalFindRootTreeOfGivenTree(eventTreeable);
	}

	private <T extends Treeable> T internalFindRootTreeOfGivenTree(T eventTreeable) {

		Treeable rtnEvent = eventTreeable.getTreeModule().getParent();

		if (rtnEvent == null) {
			return eventTreeable;
		} else {
			internalFindRootTreeOfGivenTree(eventTreeable);
		}

		return eventTreeable;

	}

}
