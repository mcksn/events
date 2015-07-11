package uk.co.mcksn.events.blackbox.util.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceUtil {

	private static ExecutorService executorService = null;
	private static int THREAD_POOL_SIZE = 10;

	public static ExecutorService getExecutorService() {
		if (executorService == null) {
			executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		}

		return executorService;
	}

	public static void close() {
		if (executorService != null) {
			executorService.shutdown();
		}
	}

}
