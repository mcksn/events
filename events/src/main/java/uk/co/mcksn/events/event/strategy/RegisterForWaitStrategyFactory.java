package uk.co.mcksn.events.event.strategy;

import java.util.ArrayList;
import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.landscape.LandscapeResolver;
import uk.co.mcksn.events.story.AbstractStoryLandscape;

public class RegisterForWaitStrategyFactory {
	
	private List<AbstractStoryLandscape<? extends Event>> availableLandscapes = new ArrayList<AbstractStoryLandscape<? extends Event>>();
	
	
	
	public RegisterForWaitStrategyFactory(List<AbstractStoryLandscape<? extends Event>> availableLandscapes) {
		super();
		this.availableLandscapes = availableLandscapes;
	}


	public RegisterForWaitStrategy createRegisterForWaitStrategy(Event event)
	{
		return findSuitableLandscape(event).getRegisterForWaitStrategy();
	}
	

	private AbstractStoryLandscape<? extends Event> findSuitableLandscape(Event event) {
		return LandscapeResolver.findApplicableLandscape(event, availableLandscapes);
	}


}
