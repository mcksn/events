package uk.co.mcksn.events.event.multi;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.mcksn.events.event.EventState;

public abstract class OperatorEventTree implements EventTree {

	private ComplexEvent complexEvent = null;
	private EventTree parent;
	protected Collection<EventTree> leaves = new ArrayList<EventTree>();

	public EventTree getParent() {
		return parent;
	}

	public void setParent(EventTree parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(EventTree rootParent) {
		setParent(rootParent);
		for (EventTree eventTree : leaves) {
			eventTree.setParent(this);
		}
	}

	public ComplexEvent getComplexEvent() {
		if (complexEvent == null)
			return new ComplexEvent((EventTree) this);
		else
			return complexEvent;
	}

	public abstract EventState getUpdatedState();

}