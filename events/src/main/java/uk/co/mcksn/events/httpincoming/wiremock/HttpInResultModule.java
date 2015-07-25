package uk.co.mcksn.events.httpincoming.wiremock;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;

import uk.co.mcksn.events.event.module.result.ResultModule;

public class HttpInResultModule implements ResultModule {

	private Queue<LoggedRequest> loggedRequests = new ConcurrentLinkedQueue<LoggedRequest>();

	public int getLoggedRequestCount() {
		return loggedRequests.size();
	}

	public void addLoggedRequest(LoggedRequest loggedRequest) {
		this.loggedRequests.add(loggedRequest);
	}

	public Iterator<LoggedRequest> getLoggedRequestIterator() {
		return loggedRequests.iterator();
	}

}
