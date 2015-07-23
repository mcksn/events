package uk.co.mcksn.events.eventstream;

class StreamDecorator {

	private EventStream eventStream = null;
	private ThenStream thenStream = null;
	private OccursThenStream occursThenStream = null;

	protected StreamDecorator(EventStream eventStream) {
		super();
		this.eventStream = eventStream;
	}

	protected EventStream getEventStream() {
		return eventStream;
	}

	protected void setEventStream(EventStream eventStream) {
		this.eventStream = eventStream;
	}

	protected OccursThenStream getOccursThenStream() {
		if (occursThenStream == null) {
			occursThenStream = new OccursThenStream(eventStream);
		}
		return occursThenStream;
	}

	protected ThenStream getThenStream() {
		if (thenStream == null) {
			thenStream = new ThenStream(eventStream);
		}
		return thenStream;
	}

	public EventStream and_the_eventStream_continues()
	{
		return getEventStream();
	}
	
	public EventStream getStream()
	{
		getEventStream().getStream();
		return getEventStream();
	}

}
