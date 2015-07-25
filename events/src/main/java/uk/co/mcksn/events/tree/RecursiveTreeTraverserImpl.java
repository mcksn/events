package uk.co.mcksn.events.tree;

@SuppressWarnings("rawtypes")
public class RecursiveTreeTraverserImpl implements TreeTraverser {

	@SuppressWarnings("unchecked")
	public <T extends Treeable> T getRootEventTreeable(Class<T> returnType, Treeable eventTreeable) {

		return (T) internalFindRootTreeOfGivenTree(eventTreeable);
	}

	private Treeable internalFindRootTreeOfGivenTree(Treeable treeable) {

		Treeable rtnEvent = (Treeable) treeable.getTreeModule().getParent();
		
		if (rtnEvent == null) {
			return treeable;
		} else {
			internalFindRootTreeOfGivenTree(treeable);
		}

		return treeable;

	}

}
