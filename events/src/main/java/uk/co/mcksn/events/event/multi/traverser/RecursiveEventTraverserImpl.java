package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.tree.EventTreeable;

public class RecursiveEventTraverserImpl implements EventTreeTraverser {


	public <T extends EventTreeable> T  getRootEventTreeable(T eventTreeable) {

		return internalFindRootTreeOfGivenTree(eventTreeable);
	}

	private <T extends EventTreeable> T internalFindRootTreeOfGivenTree(T eventTreeable) {

		EventTreeable rtnEvent = eventTreeable.getTreeModule().getParent();

		if (rtnEvent == null) {
			return eventTreeable;
		} else {
			internalFindRootTreeOfGivenTree(eventTreeable);
		}

		return eventTreeable;

	}

}
