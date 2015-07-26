package uk.co.mcksn.events.test.waitable.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import uk.co.mcksn.events.event.module.result.ResultModule;

public class TestUtilWaitableResultModule implements ResultModule {

	private List<LoggedRequest> loggedRequests = new ArrayList<LoggedRequest>();

	public int getLoggedRequestCount() {
		return loggedRequests.size();
	}

	public void addLoggedRequest(LoggedRequest loggedRequest) {
		this.loggedRequests.add(loggedRequest);
	}

	public List<LoggedRequest> getLoggedRequestIterator() {
		return new ArrayList<LoggedRequest>(loggedRequests);

	}

}
