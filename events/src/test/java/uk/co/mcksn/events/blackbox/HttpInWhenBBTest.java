package uk.co.mcksn.events.blackbox;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;

import uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration;
import uk.co.mcksn.events.behavior.common.junit.AbstractWhenPlotBehavior;
import uk.co.mcksn.events.blackbox.util.common.UtilityProvider;
import uk.co.mcksn.events.blackbox.util.wiremock.notifier.WireMockNotifierImpl;
import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.complex.WaitComplexEventBuilder;
import uk.co.mcksn.events.eventstream.AbstractEventHandler;
import uk.co.mcksn.events.eventstream.EventStream;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEvent;
import uk.co.mcksn.events.httpincoming.wiremock.HttpInEventHandler;
import uk.co.mcksn.events.httpincoming.wiremock.WireMockServerDef;

public class HttpInWhenBBTest extends AbstractWhenPlotBehavior {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpInWhenBBTest.class);

	private static final String COMPONENT_B_URL = "/compB";
	private static final String COMPONENT_C_URL = "/compC";

	private static String TEST_UTILITY_URL = "http://localhost:8080/stub-srr-when/invokeTest";

	private static UtilityProvider utilityProvider = null;

	private AbstractEventHandler httpInEventHandler = null;

	private WireMockServerDef wireMockServerDefB = WireMockServerDef.buildDefintion(
			new WireMockServer(wireMockConfig().notifier(new WireMockNotifierImpl()).port(8051)), "Comp B");

	private WireMockServerDef wireMockServerDefC = WireMockServerDef.buildDefintion(
			new WireMockServer(wireMockConfig().notifier(new WireMockNotifierImpl()).port(8052)), "Comp C");

	private HttpInEvent defineBaseEventRequests(WireMockServerDef respondingServer, final String url,
			AbstractWhenPlotBehaviorEnumeration behaviorEnum, String requestDiscriminator) {

		if (requestDiscriminator != null)
			requestDiscriminator = "/" + requestDiscriminator;
		else
			requestDiscriminator = "";

		HttpInEvent event = new HttpInEvent();
		MappingBuilder mappingBuilder = get(urlEqualTo(url + requestDiscriminator));

		event.getActionModule().givenRequest(mappingBuilder.willReturn(aResponse().withBody("a response")));

		event.getWaitModule().setTimeout(2000L);
		event.setWireMockServerDef(respondingServer);
		return event;

	}

	private HttpInEvent defineRequestsToCompB(AbstractWhenPlotBehaviorEnumeration behaviorEnum,
			String requestDiscriminator) {

		HttpInEvent event = defineBaseEventRequests(wireMockServerDefB, COMPONENT_B_URL, behaviorEnum,
				requestDiscriminator);
		
		if (requestDiscriminator == null)
			requestDiscriminator = "Not name defined";
		
		event.setName("Event: " + requestDiscriminator);
		return event;

	}

	@Before
	public void before() {
		httpInEventHandler = HttpInEventHandler.looksLike(wireMockServerDefB, wireMockServerDefC);
		utilityProvider = new UtilityProvider();
	}

	@After
	public void after() {

		 utilityProvider.cleanUp();

		if (wireMockServerDefB.getWireMockServer().isRunning()) {
			wireMockServerDefB.getWireMockServer().stop();
		}
		if (wireMockServerDefC.getWireMockServer().isRunning()) {
			wireMockServerDefC.getWireMockServer().stop();
		}
	}

	@Override
	@Test
	public void SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for() {

		final AbstractWhenPlotBehaviorEnumeration behaviorEnum = SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for;
		HttpInEvent reqToCompBWithResponse = defineRequestsToCompB(behaviorEnum, null);

		EventStream eventStream = EventStream.given(httpInEventHandler);

		// invoke test
		utilityProvider.getNewHttpClientInstance(TEST_UTILITY_URL).sendGet(behaviorEnum.name());

		eventStream.when(reqToCompBWithResponse);

		Assert.assertEquals(EventState.OCCURRED, reqToCompBWithResponse.getOccurredModule().getState());

		Assert.assertEquals(behaviorEnum.name(),
				reqToCompBWithResponse.getResultModule().getLoggedRequestIterator().get(0).getHeader("a header"));
	}

	@Override
	@Test
	public void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and() {

		final AbstractWhenPlotBehaviorEnumeration behaviorEnum = SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and;
		HttpInEvent reqToCompBWithResponse2 = defineRequestsToCompB(behaviorEnum, "2");
		HttpInEvent reqToCompBWithResponse1 = defineRequestsToCompB(behaviorEnum, "1");

		EventStream eventStream = EventStream.given(httpInEventHandler);

		// invoke test utility
		utilityProvider.getNewHttpClientInstance(TEST_UTILITY_URL).sendGet(behaviorEnum.name());

		eventStream.when(WaitComplexEventBuilder.and(reqToCompBWithResponse1, reqToCompBWithResponse2));

		Assert.assertEquals(EventState.OCCURRED,
				reqToCompBWithResponse1.getTreeModule().getParent().getOccurredModule().getState());
		Assert.assertEquals(EventState.OCCURRED,
				reqToCompBWithResponse2.getTreeModule().getParent().getOccurredModule().getState());
		Assert.assertEquals(EventState.OCCURRED, reqToCompBWithResponse1.getOccurredModule().getState());
		Assert.assertEquals(EventState.OCCURRED, reqToCompBWithResponse2.getOccurredModule().getState());

	}

	@Override
	@Test
	public void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or() {

		final AbstractWhenPlotBehaviorEnumeration behaviorEnum = SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or;
		HttpInEvent reqToCompBWithResponse1 = defineRequestsToCompB(behaviorEnum, "1");
		HttpInEvent reqToCompBWithResponse2 = defineRequestsToCompB(behaviorEnum, "2");

		EventStream eventStream = EventStream.given(httpInEventHandler);

		// invoke test utility
		utilityProvider.getNewHttpClientInstance(TEST_UTILITY_URL).sendGet(behaviorEnum.name());

		eventStream.when(WaitComplexEventBuilder.or(reqToCompBWithResponse1, reqToCompBWithResponse2));

		Assert.assertEquals(EventState.OCCURRED,
				reqToCompBWithResponse1.getTreeModule().getParent().getOccurredModule().getState());

	}

	@Override
	@Test
	public void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or() {

		final AbstractWhenPlotBehaviorEnumeration behaviorEnum = SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or;
		HttpInEvent reqToCompBWithResponse1 = defineRequestsToCompB(behaviorEnum, "1");
		HttpInEvent reqToCompBWithResponse2 = defineRequestsToCompB(behaviorEnum, "2");
		HttpInEvent reqToCompBWithResponse3 = defineRequestsToCompB(behaviorEnum, "3");

		ComplexEvent andEvent = WaitComplexEventBuilder.and(reqToCompBWithResponse1, reqToCompBWithResponse2);
		ComplexEvent orEvent = WaitComplexEventBuilder.or(andEvent, reqToCompBWithResponse3);

		orEvent.getTreeModule().printTree(0);

		EventStream eventStream = EventStream.given(httpInEventHandler);

		// invoke test utility
		utilityProvider.getNewHttpClientInstance(TEST_UTILITY_URL).sendGet(behaviorEnum.name());

		eventStream.when(orEvent);

		Assert.assertEquals(EventState.OCCURRED, orEvent.getOccurredModule().getState());

	}

	/** @Override public voidSHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or*
	*{ AbstractEvent reqToCompBWithResponse1 =
	 * defineRequestsToCompB("Response 1"); AbstractEvent
	 * reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");
	 * ComplexEvent complexEvent = or(reqToCompBWithResponse1,
	 * reqToCompBWithResponse2).getComplexEvent();
	 * 
	 * //@formatter:off EventStream story = EventStream.given(httpInEventHandler)
	 * .when(complexEvent) .occurs_then_verify(complexEvent)
	 * .and_the_story_continues();
	 * 
	 * story.logStorySoFar(); //@formatter:on
	 * 
	 * }**@Override public void*SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or*()

	{
	 * 
	 * AbstractEvent reqToCompBWithResponse1 =
	 * defineRequestsToCompB("Response 1"); AbstractEvent
	 * reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");
	 * AbstractEvent reqToCompBWithResponse3 =
	 * defineRequestsToCompB("Response 3");
	 * 
	 * ComplexEvent complexEvent = or(reqToCompBWithResponse1,
	 * and(reqToCompBWithResponse2, reqToCompBWithResponse3))
	 * .getComplexEvent();
	 * 
	 * //@formatter:off EventStream story = EventStream.given(httpInEventHandler)
	 * .when(complexEvent) .occurs_then_verify(complexEvent)
	 * .and_the_story_continues();
	 * 
	 * story.logStorySoFar(); //@formatter:on
	 * 
	 * }*/
}