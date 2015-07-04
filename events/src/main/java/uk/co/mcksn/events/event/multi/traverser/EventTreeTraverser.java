package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventTree;

public interface EventTreeTraverser {
	
	ComplexEvent getComplexEventOfRootTree(EventTree event);

}
