package uk.co.mcksn.events.blackbox.util.apache.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.blackbox.util.common.CleanUpable;

public class ApacheHttpClientUtil implements CleanUpable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApacheHttpClientUtil.class);

	private String url = null;
	private HttpClient client = new DefaultHttpClient();
	private List<Future<?>> futureResponses = new ArrayList<Future<?>>();
	private long timeoutWaitingForRequestToComplete = 0L;

	private ExecutorService executorService = null;

	/**
	 * 
	 * @param url
	 * @param executorService
	 * @param timeoutWaitingForRequestToComplete
	 *            milliseconds
	 */
	public ApacheHttpClientUtil(String url, ExecutorService executorService, long timeoutWaitingForRequestToComplete) {
		super();
		this.url = url;
		this.executorService = executorService;
		this.timeoutWaitingForRequestToComplete = timeoutWaitingForRequestToComplete;
	}

	public void cleanUp() {
		for (Future<?> futureResponse : futureResponses) {

			try {
				HttpResponse httpResponse = (HttpResponse) futureResponse.get(timeoutWaitingForRequestToComplete,
						TimeUnit.MILLISECONDS);
			} catch (NullPointerException e) {
				LOGGER.error("Fatal nullpointer error: " + e.getMessage());
			} catch (InterruptedException e) {
				LOGGER.error("Fatal interrupted error: " + e.getMessage());
			} catch (ExecutionException e) {
				LOGGER.error("Fatal execution error: " + e.getMessage());
			} catch (TimeoutException e) {
				LOGGER.error("Fatal timeout error waiting for http request to complete: " + e.getMessage());
			}
		}
	}

	public Future<Object> sendGet(final String behaviorName) {

		Future<Object> futureResponse = executorService.submit(new Callable<Object>() {
			public Object call() throws Exception {

				final String URL_WITH_BEHAVIOR_NAME = url + "?behaviorName=" + behaviorName;

				HttpGet request = new HttpGet(URL_WITH_BEHAVIOR_NAME);

				LOGGER.info("Sending 'GET' request to URL : " + URL_WITH_BEHAVIOR_NAME);

				HttpResponse response = client.execute(request);

				LOGGER.info("Response Code : " + response.getStatusLine().getStatusCode());

				return response;
			}
		});
		futureResponses.add(futureResponse);

		return futureResponse;
	}

	public List<Future<?>> getFutureResponses() {
		return futureResponses;
	}

	public void setFutureResponses(List<Future<?>> futureResponses) {
		this.futureResponses = futureResponses;
	}

}
