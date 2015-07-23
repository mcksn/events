package uk.co.mcksn.events.tree;

public interface TreeTraverser {

	<T extends Treeable> T getRootEventTreeable(T eventTreeable);

}
