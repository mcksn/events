package uk.co.mcksn.events.exception;

import uk.co.mcksn.events.event.Event;

public class VerificationNtSuccessfulException extends RuntimeException {

	public VerificationNtSuccessfulException(Event event) {
		super("Verfication Failure for:" + event.getName());
		this.event = event;
	}

	private Event event;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
