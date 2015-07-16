package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.tree.EventTreeable;

public interface EventTreeTraverser {

	<T extends EventTreeable> T getRootEventTreeable(T eventTreeable);

}
