package uk.co.mcksn.events.blackbox.util.apache.httpclient;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import uk.co.mcksn.events.blackbox.util.common.ExecutorServiceUtil;

public class ApacheHttpClientUtil implements Closeable {

	private static final Logger LOGGER = Logger.getLogger(ApacheHttpClientUtil.class.getCanonicalName());

	private String url = null;
	private ExecutorService executorService = ExecutorServiceUtil.getExecutorService();

	public ApacheHttpClientUtil(String url) {
		super();
		this.url = url;
	}

	@Override
	public void close() throws IOException {
		executorService.shutdownNow();
	}

	public void sendGet(final String behaviorName) {

		final HttpClient client = new DefaultHttpClient();

		executorService.submit(new Callable<Object>() {
			public Object call() throws Exception {

				final String URL_WITH_BEHAVIOR_NAME = url + "?behaviorName=" + behaviorName;

				HttpGet request = new HttpGet(URL_WITH_BEHAVIOR_NAME);

				LOGGER.info("\nSending 'GET' request to URL : " + URL_WITH_BEHAVIOR_NAME);

				HttpResponse response = client.execute(request);

				LOGGER.info("Response Code : " + response.getStatusLine().getStatusCode());

				return response;
			}
		});

		/*
		 * try { future.get(); } catch (InterruptedException |
		 * ExecutionException ex) { LOGGER.severe(
		 * "Exception occurred whilst sending 'GET' request"); throw new
		 * RuntimeException(ex); }
		 */

	}

}
