package uk.co.mcksn.events.incominghttp.wiremock;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import uk.co.mcksn.events.event.module.result.ResultModule;

public class ServerRecievesRequestResultModule implements ResultModule {

	private LoggedRequest loggedRequest;

	public LoggedRequest getLoggedRequest() {
		return loggedRequest;
	}

	public void setLoggedRequest(LoggedRequest loggedRequest) {
		this.loggedRequest = loggedRequest;
	}

}
