package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventTreeable;

public class RecursiveEventTraverserImpl implements EventTreeTraverser {

	public ComplexEvent getComplexEventOfRootTree(EventTreeable eventTree) {

		return internalFindRootTreeOfGivenTree(eventTree).getComplexEvent();
	}

	private EventTreeable internalFindRootTreeOfGivenTree(EventTreeable eventTree) {
		
		EventTreeable eventTreeParent = eventTree.getParent();
		
		if (eventTreeParent == null) {
			return eventTree;
		} else {
			internalFindRootTreeOfGivenTree(eventTreeParent);
		}

		return eventTreeParent;

	}

}
