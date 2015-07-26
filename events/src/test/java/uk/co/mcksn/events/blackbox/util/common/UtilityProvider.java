package uk.co.mcksn.events.blackbox.util.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.co.mcksn.events.blackbox.util.apache.httpclient.ApacheHttpClientUtil;
import uk.co.mcksn.events.blackbox.util.wiremock.notifier.WireMockNotifierImpl;

public class UtilityProvider implements CleanUpable {

	public ExecutorService executorService = ExecutorServiceSingleton.getNewInstance();
	public Collection<ApacheHttpClientUtil> apacheHttpClientUtils = new ArrayList<ApacheHttpClientUtil>();
	private long TIMEOUT_WAITING_FOR_HTTP_CLIENTREQUEST_TO_COMPLETE = 800L;

	public ApacheHttpClientUtil getNewHttpClientInstance(String url) {
		ApacheHttpClientUtil apacheHttpClientUtil = new ApacheHttpClientUtil(url, executorService,
				TIMEOUT_WAITING_FOR_HTTP_CLIENTREQUEST_TO_COMPLETE);
		apacheHttpClientUtils.add(apacheHttpClientUtil);

		return apacheHttpClientUtil;

	}

	public WireMockNotifierImpl getNewWireMockNotifierInstance(String url) {
		return new WireMockNotifierImpl();

	}

	public void cleanUp() {
		ExecutorServiceSingleton.cleanUp();
		for (ApacheHttpClientUtil util : apacheHttpClientUtils) {
			util.cleanUp();
		}

	}

	static class ExecutorServiceSingleton {

		private static final int THREAD_POOL_SIZE = 10;

		private static ExecutorService executorService = null;

		public static ExecutorService getNewInstance() {
			executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			return executorService;
		}

		public static void cleanUp() {
			if (executorService != null) {
				executorService.shutdown();
			}
		}

	}

};