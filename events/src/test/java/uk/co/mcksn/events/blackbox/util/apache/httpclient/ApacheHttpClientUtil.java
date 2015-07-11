package uk.co.mcksn.events.blackbox.util.apache.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import uk.co.mcksn.events.blackbox.util.common.CleanUpable;

public class ApacheHttpClientUtil implements CleanUpable {

	private static final Logger LOGGER = Logger.getLogger(ApacheHttpClientUtil.class.getCanonicalName());

	private String url = null;
	private HttpClient client = new DefaultHttpClient();
	public List<Future<?>> futureResponses = new ArrayList<Future<?>>();

	private ExecutorService executorService = null;

	public ApacheHttpClientUtil(String url, ExecutorService executorService) {
		super();
		this.url = url;
		this.executorService = executorService;
	}

	@Override
	public void cleanUp() {
		for (Future<?> futureResponse : futureResponses) {

		}
		// cycle through and

		/*
		 * } catch (HttpException e) { System.err.println(
		 * "Fatal protocol violation: " + e.getMessage()); e.printStackTrace();
		 * } catch (IOException e) { System.err.println(
		 * "Fatal transport error: " + e.getMessage()); e.printStackTrace(); }
		 * finally { // Release the connection. method.releaseConnection(); }
		 */

	}

	public Future<Object> sendGet(final String behaviorName) {

		Future<Object> futureResponse = null;
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
