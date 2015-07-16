package uk.co.mcksn.events.event.strategy;

import java.util.ArrayList;
import java.util.List;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.landscape.LandscapeResolver;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.story.AbstractStoryLandscape;

public class VerificationStrategyFactory {
	
	private List<AbstractStoryLandscape<? extends Event>> availableLandscapes = new ArrayList<AbstractStoryLandscape<? extends Event>>();
	
	
	
	public VerificationStrategyFactory(List<AbstractStoryLandscape<? extends Event>> availableLandscapes) {
		super();
		this.availableLandscapes = availableLandscapes;
	}


	public VerificationStrategy createVerificationStrategy(VerifyPlotable verifyPlotable)
	{
		return findSuitableLandscape((Event)verifyPlotable).getVerificationStrategy();
	}
	

	private AbstractStoryLandscape<? extends Event> findSuitableLandscape(Event event) {
		return LandscapeResolver.findApplicableLandscape(event, availableLandscapes);
	}


}
