package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.tree.EventTreeable;

public interface EventTreeTraverser {
	
	ComplexEvent getComplexEventOfRootTree(EventTreeable event);

}
