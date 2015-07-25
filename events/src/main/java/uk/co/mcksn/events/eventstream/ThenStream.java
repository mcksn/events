package uk.co.mcksn.events.eventstream;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.type.Expectable;
import uk.co.mcksn.events.type.Simulateable;
import uk.co.mcksn.events.type.Whenable;

@SuppressWarnings("rawtypes")
public class ThenStream extends StreamDecorator {

	ThenStream(EventStream eventStream) {
		super(eventStream);
	}

	public <SimulatePlotableEvent extends Event & Simulateable> ThenStream then_simulate(SimulatePlotableEvent simulatePlotableEvent) {
		getEventStream().simulate(simulatePlotableEvent);
		return this;
	}

	public <ExpectPlotableEvent extends Event & Expectable> ThenStream then_expect(ExpectPlotableEvent expectPlotableEvent) {
		getEventStream().expect(expectPlotableEvent);
		return this;
	}

	public <WhenPlotableEvent extends Event & Whenable> OccursThenStream and_when(WhenPlotableEvent whenPlotableEvent) {
		getEventStream().when(whenPlotableEvent);
		return getOccursThenStream();
	}

}