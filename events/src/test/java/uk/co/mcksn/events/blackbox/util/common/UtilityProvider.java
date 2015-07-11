package uk.co.mcksn.events.blackbox.util.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.co.mcksn.events.blackbox.util.apache.httpclient.ApacheHttpClientUtil;

public class UtilityProvider implements CleanUpable {

	public ExecutorService executorService = ExecutorServiceSingleton.getInstance();
	public Collection<ApacheHttpClientUtil> apacheHttpClientUtils = new ArrayList<ApacheHttpClientUtil>();

	public ApacheHttpClientUtil getHttpClient(String url) {
		ApacheHttpClientUtil apacheHttpClientUtil = new ApacheHttpClientUtil(url, executorService);
		apacheHttpClientUtils.add(apacheHttpClientUtil);

		return apacheHttpClientUtil;

	}

	public void cleanUp() {
		ExecutorServiceSingleton.cleanUp();
		for (ApacheHttpClientUtil util : apacheHttpClientUtils)
		{
			//util.c
		}
			
	}

	static class ExecutorServiceSingleton {

		private static final int THREAD_POOL_SIZE = 10;

		private static ExecutorService executorService = null;

		public static ExecutorService getInstance() {
			if (executorService == null) {
				executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			}

			return executorService;
		}

		public static void cleanUp() {
			if (executorService != null) {
				executorService.shutdown();
			}
		}

	}

};