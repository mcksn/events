package uk.co.mcksn.events.event.result;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;

public class ServerRecievesRequestResultModule implements ResultModule {

	private LoggedRequest loggedRequest;

	public LoggedRequest getLoggedRequest() {
		return loggedRequest;
	}

	public void setLoggedRequest(LoggedRequest loggedRequest) {
		this.loggedRequest = loggedRequest;
	}

}
