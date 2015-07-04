package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventTree;

public class RecursiveEventTraverserImpl implements EventTreeTraverser {

	public ComplexEvent getComplexEventOfRootTree(EventTree eventTree) {

		return internalFindRootTreeOfGivenTree(eventTree).getComplexEvent();
	}

	private EventTree internalFindRootTreeOfGivenTree(EventTree eventTree) {
		
		EventTree eventTreeParent = eventTree.getParent();
		
		if (eventTreeParent == null) {
			return eventTree;
		} else {
			internalFindRootTreeOfGivenTree(eventTreeParent);
		}

		return eventTreeParent;

	}

}
