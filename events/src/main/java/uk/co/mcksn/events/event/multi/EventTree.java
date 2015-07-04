package uk.co.mcksn.events.event.multi;

import uk.co.mcksn.events.event.EventState;

public interface EventTree {

	ComplexEvent getComplexEvent();

	EventTree getParent();

	void setParent(EventTree parent);
	
	void setParentsOfAllChildren(EventTree rootParent);
	
	EventState getUpdatedState();


}