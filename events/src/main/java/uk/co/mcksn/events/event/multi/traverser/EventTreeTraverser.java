package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventTreeable;

public interface EventTreeTraverser {
	
	ComplexEvent getComplexEventOfRootTree(EventTreeable event);

}
