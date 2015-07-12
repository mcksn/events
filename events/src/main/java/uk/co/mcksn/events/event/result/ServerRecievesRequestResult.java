package uk.co.mcksn.events.event.result;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;

public class ServerRecievesRequestResult implements Result {

	private LoggedRequest loggedRequest;

	public LoggedRequest getLoggedRequest() {
		return loggedRequest;
	}

	public void setLoggedRequest(LoggedRequest loggedRequest) {
		this.loggedRequest = loggedRequest;
	}

}
