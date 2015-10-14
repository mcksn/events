package uk.co.mcksn.events.stub.srr.controller;

import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration;

@RequestMapping(value = "/*")
@RestController
@Scope("prototype")
public class MainController {

	private static final String REQUEST_DISCRIMINATOR_PLACEHOLDER = "/{requestDiscriminator}";

	@Autowired
	private RestTemplate restClient;

	private static final String MSG_BEHAVIOR_NAME_SUPPLIED_COULD_NOT_BE_MATCHED_WITH_A_STUB = "Behavior name supplied could not be matched with a stub";
	
	private static final Logger LOGGER = Logger.getLogger(MainController.class.getCanonicalName());
	
	private static final String COMPONENT_B_URL = "http://localhost:8051/compB";

	private static final ResponseEntity<String> response1 = new ResponseEntity<String>("Response 1",
			HttpStatus.ACCEPTED);
	
	private static final ResponseEntity<String> response2 = new ResponseEntity<String>("Response 2",
			HttpStatus.ACCEPTED);

	@RequestMapping(AbstractWhenPlotBehaviorEnumeration.Constant.SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for)
	public ResponseEntity<String>  SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for()
			throws Exception {

		AbstractWhenPlotBehaviorEnumeration behaviorEnum = null;
		HttpHeaders headers = null;
		HttpEntity<String> requestEntity = null;

		headers = new HttpHeaders();
		headers.add("a header", getMethodName());
		requestEntity = new HttpEntity<String>(headers);

		restClient.exchange(COMPONENT_B_URL, HttpMethod.GET, requestEntity, String.class);

		return response1;
	}

	@RequestMapping(AbstractWhenPlotBehaviorEnumeration.Constant.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and)
	public ResponseEntity<String> SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and()
			throws Exception {

		HttpHeaders headers = null;
		HttpEntity<String> requestEntity = null;

		headers = new HttpHeaders();
		headers.add("a header", getMethodName());
		requestEntity = new HttpEntity<String>(headers);


		restClient.exchange(COMPONENT_B_URL + REQUEST_DISCRIMINATOR_PLACEHOLDER, HttpMethod.GET, requestEntity, String.class,
				"1");
		restClient.exchange(COMPONENT_B_URL + REQUEST_DISCRIMINATOR_PLACEHOLDER, HttpMethod.GET, requestEntity, String.class,
				"2");

		return response1;

	}


	@RequestMapping(AbstractWhenPlotBehaviorEnumeration.Constant.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or)
	public ResponseEntity<String> SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or()
			throws Exception {

		AbstractWhenPlotBehaviorEnumeration behaviorEnum = null;
		HttpHeaders headers = null;
		HttpEntity<String> requestEntity = null;

		headers = new HttpHeaders();
		headers.add("a header", getMethodName());
		requestEntity = new HttpEntity<String>(headers);

		ResponseEntity<String> rtnValue = response1;

		if (new Random().nextInt() % 2 == 0) {
			restClient.exchange(COMPONENT_B_URL + REQUEST_DISCRIMINATOR_PLACEHOLDER, HttpMethod.GET, requestEntity,
					String.class, "1");
		} else {
			restClient.exchange(COMPONENT_B_URL + REQUEST_DISCRIMINATOR_PLACEHOLDER, HttpMethod.GET, requestEntity,
					String.class, "2");
		}

		return response1;

	}

	@RequestMapping(AbstractWhenPlotBehaviorEnumeration.Constant.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or)
	public ResponseEntity<String> SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or()
			throws Exception {

		AbstractWhenPlotBehaviorEnumeration behaviorEnum = null;
		HttpHeaders headers = null;
		HttpEntity<String> requestEntity = null;

		headers = new HttpHeaders();
		headers.add("a header", getMethodName());
		requestEntity = new HttpEntity<String>(headers);


		if (new Random().nextInt() % 2 == 0) {
			restClient.exchange(COMPONENT_B_URL + REQUEST_DISCRIMINATOR_PLACEHOLDER, HttpMethod.GET, requestEntity,
					String.class, "1");
			restClient.exchange(COMPONENT_B_URL + REQUEST_DISCRIMINATOR_PLACEHOLDER, HttpMethod.GET, requestEntity,
					String.class, "2");
		} else {

			restClient.exchange(COMPONENT_B_URL + REQUEST_DISCRIMINATOR_PLACEHOLDER, HttpMethod.GET, requestEntity,
					String.class, "3");
		}

		return response1;

	}
	

	private String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

}
