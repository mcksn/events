package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.type.Simulateable;

@SuppressWarnings("rawtypes")
public class OccursThenStream extends StreamDecorator {

	OccursThenStream(EventStream eventStream) {
		super(eventStream);
	}

	public <SimulatePlotableEvent extends Event & Simulateable> ThenStream  occursThenSimulate(SimulatePlotableEvent simulatePlotableEvent) {
		getEventStream().simulate(simulatePlotableEvent);
		return getThenStream();
	}

}
