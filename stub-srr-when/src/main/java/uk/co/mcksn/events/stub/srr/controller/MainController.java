package uk.co.mcksn.events.stub.srr.controller;

import static java.lang.Enum.valueOf;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or;

import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration;
import uk.co.mcksn.events.behavior.common.junit.AbstractWhenPlotBehavior;

@RequestMapping(value = "/*")
@RestController
public class MainController extends AbstractWhenPlotBehavior {

	@Autowired
	private RestTemplate restClient;

	private static final String MSG_BEHAVIOR_NAME_SUPPLIED_COULD_NOT_BE_MATCHED_WITH_A_STUB = "Behavior name supplied could not be matched with a stub";
	private static final Logger LOGGER = Logger.getLogger(MainController.class.getCanonicalName());
	private static final String COMPONENT_B_URL = "http://localhost:8051/compB";

	private static final ResponseEntity<String> response1 = new ResponseEntity<String>("Response 1",
			HttpStatus.ACCEPTED);
	private static final ResponseEntity<String> response2 = new ResponseEntity<String>("Response 2",
			HttpStatus.ACCEPTED);

	@RequestMapping
	public ResponseEntity<String> invokeTest(@RequestParam("behaviorName") String name) throws Exception {

		AbstractWhenPlotBehaviorEnumeration behaviorEnum = null;
		HttpHeaders headers = null;
		HttpEntity<String> requestEntity = null;

		try {
			behaviorEnum = valueOf(AbstractWhenPlotBehaviorEnumeration.class, name);
		} catch (IllegalArgumentException illegalArgumentException) {
			LOGGER.severe(MSG_BEHAVIOR_NAME_SUPPLIED_COULD_NOT_BE_MATCHED_WITH_A_STUB);
			throw illegalArgumentException;
		}

		headers = new HttpHeaders();
		headers.add("a header", behaviorEnum.name());
		requestEntity = new HttpEntity<String>(headers);

		ResponseEntity<String> rtnValue = response1;

		switch (behaviorEnum) {
		case SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for:

			restClient.exchange(COMPONENT_B_URL, HttpMethod.GET, requestEntity, String.class);
			break;
		case SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and:

			restClient.exchange(COMPONENT_B_URL + "/{requestDiscriminator}", HttpMethod.GET, requestEntity,
					String.class, "1");
			restClient.exchange(COMPONENT_B_URL + "/{requestDiscriminator}", HttpMethod.GET, requestEntity,
					String.class, "2");
			break;

		case SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or:

			if (new Random().nextInt() % 2 == 0) {
				restClient.exchange(COMPONENT_B_URL + "/{requestDiscriminator}", HttpMethod.GET, requestEntity,
						String.class, "1");
			} else {
				restClient.exchange(COMPONENT_B_URL + "/{requestDiscriminator}", HttpMethod.GET, requestEntity,
						String.class, "2");
			}
			break;
		case SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or:

			if (new Random().nextInt() % 2 == 0) {
				restClient.exchange(COMPONENT_B_URL + "/{requestDiscriminator}", HttpMethod.GET, requestEntity,
						String.class, "1");
				restClient.exchange(COMPONENT_B_URL + "/{requestDiscriminator}", HttpMethod.GET, requestEntity,
						String.class, "2");
			} else {

				restClient.exchange(COMPONENT_B_URL + "/{requestDiscriminator}", HttpMethod.GET, requestEntity,
						String.class, "3");
			}
			break;
		default:
			throw new Exception(MSG_BEHAVIOR_NAME_SUPPLIED_COULD_NOT_BE_MATCHED_WITH_A_STUB);
		}

		return rtnValue;
	}
}
