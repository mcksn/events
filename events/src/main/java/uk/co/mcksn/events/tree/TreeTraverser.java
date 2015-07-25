package uk.co.mcksn.events.tree;

@SuppressWarnings("rawtypes")
public interface TreeTraverser {

	<T extends Treeable> T getRootEventTreeable(Class<T> returnType, Treeable treeable);
}
