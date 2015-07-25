package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.type.Simulateable;

@SuppressWarnings("rawtypes")
public interface SimulateHandlerable {
	
	<SimulateableEvent extends Event & Simulateable> void simulate(
			SimulateableEvent simulateableEvent);

}
