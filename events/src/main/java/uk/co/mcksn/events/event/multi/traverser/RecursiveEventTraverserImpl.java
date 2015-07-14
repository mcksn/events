package uk.co.mcksn.events.event.multi.traverser;

import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventTreeable;

public class RecursiveEventTraverserImpl implements EventTreeTraverser {

	/**
	 *TODO Fix by using tree module
	 * @param event
	 * @return
	 */
	public ComplexEvent getComplexEventOfRootTree(Event event) {

		return internalFindRootTreeOfGivenTree(event);
	}

	private Event internalFindRootTreeOfGivenTree(Event event) {
		
		ComplexEvent complexEvent = null;
		if (event instanceof ComplexEvent)
		{
			complexEvent = ((ComplexEvent)event).getParent();
			
			if (complexEvent == null) {
				return (ComplexEvent)event);
			} else {
				internalFindRootTreeOfGivenTree(complexEvent);
			}
		}
		else {
			return event;
		}
		


		return complexEvent;

	}

}
