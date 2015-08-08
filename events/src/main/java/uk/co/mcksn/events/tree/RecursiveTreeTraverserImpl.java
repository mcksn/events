package uk.co.mcksn.events.tree;

@SuppressWarnings("rawtypes")
public class RecursiveTreeTraverserImpl implements TreeTraverser {

	@SuppressWarnings("unchecked")
	public <T extends Treeable> T getRootEventTreeable(Class<T> returnType, Treeable treeable) {

		return (T) internalFindRootTreeOfGivenTree(treeable);
	}

	private Treeable internalFindRootTreeOfGivenTree(Treeable treeable) {

		Treeable nextTreeUp = (Treeable) treeable.getTreeModule().getParent();

		if (nextTreeUp == null) {
			return treeable;
		} else {
			nextTreeUp = internalFindRootTreeOfGivenTree(nextTreeUp);
		}

		return nextTreeUp;

	}

}
