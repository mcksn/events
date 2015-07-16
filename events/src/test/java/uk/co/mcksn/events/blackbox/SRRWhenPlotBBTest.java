package uk.co.mcksn.events.blackbox;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.WireMockServer;

import uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration;
import uk.co.mcksn.events.behavior.common.junit.AbstractWhenPlotBehavior;
import uk.co.mcksn.events.blackbox.util.apache.httpclient.ApacheHttpClientUtil;
import uk.co.mcksn.events.blackbox.util.common.UtilityProvider;
import uk.co.mcksn.events.blackbox.util.wiremock.notifier.WireMockNotifierImpl;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.landscape.ServerReceivesRequestLandscape;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.story.AbstractStoryLandscape;
import uk.co.mcksn.events.story.Story;

public class SRRWhenPlotBBTest extends AbstractWhenPlotBehavior {

	private static final Logger LOGGER = LoggerFactory.getLogger(SRRWhenPlotBBTest.class);

	private static final String COMPONENT_B_URL = "/compB";
	private static final String COMPONENT_C_URL = "/compC";

	private static String STUB_URL = "http://localhost:8080/stub-srr-when/invokeTest";

	private static final UtilityProvider UTILITY_PROVIDER = new UtilityProvider();

	private AbstractStoryLandscape<?> storyLandscape = null;

	private WireMockServerDef wireMockServerDefB = WireMockServerDef.buildDefintion(
			new WireMockServer(wireMockConfig().notifier(new WireMockNotifierImpl()).port(8051)), "Comp B");

	private WireMockServerDef wireMockServerDefC = WireMockServerDef.buildDefintion(
			new WireMockServer(wireMockConfig().notifier(new WireMockNotifierImpl()).port(8052)), "Comp C");

	private ServerReceivesRequestEvent defineBaseEventRequests(String requestBody, WireMockServerDef respondingServer,
			final String url, AbstractWhenPlotBehaviorEnumeration behaviorEnum) {

		ServerReceivesRequestEvent event = new ServerReceivesRequestEvent();
		event.getActionModule().givenRequest(get(urlEqualTo(url)).willReturn(aResponse().withBody("a response")));
		event.getWaitModule().setTimeout(2000L);
		event.setWireMockServerDef(respondingServer);
		event.getVerificationPolicyModule()
				.verify(getRequestedFor(urlEqualTo(url)).withHeader("a header", equalTo(behaviorEnum.name())));
		return event;

	}

	private ServerReceivesRequestEvent defineRequestsToCompB(String requestBody,
			AbstractWhenPlotBehaviorEnumeration behaviorEnum) {

		ServerReceivesRequestEvent event = defineBaseEventRequests(requestBody, wireMockServerDefB, COMPONENT_B_URL,
				behaviorEnum);
		event.setName("Component A makes a request to Component B");
		event.setWireMockServerDef(wireMockServerDefB);
		return event;

	}

	@Before
	public void before() {
		storyLandscape = ServerReceivesRequestLandscape.looksLike(wireMockServerDefB, wireMockServerDefC);
	}

	@After
	public void after() {

		UTILITY_PROVIDER.cleanUp();

		if (wireMockServerDefB.getWireMockServer().isRunning()) {
			wireMockServerDefB.getWireMockServer().stop();
		}
		if (wireMockServerDefB.getWireMockServer().isRunning()) {
			wireMockServerDefC.getWireMockServer().stop();
		}
	}

	@Override
	@Test
	public void SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for() {

		final AbstractWhenPlotBehaviorEnumeration behaviorEnum = SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for;
		ServerReceivesRequestEvent reqToCompBWithResponse1 = defineRequestsToCompB("Response 1", behaviorEnum);

		
		Story story = Story.given(storyLandscape);
		
		// invoke test
		UTILITY_PROVIDER.getNewHttpClientInstance(STUB_URL).sendGet(behaviorEnum.name());
		
		story.when(reqToCompBWithResponse1);
		
		Assert.assertEquals(EventState.OCCURRED, reqToCompBWithResponse1.getEventOccurredModule().getState());
		Assert.assertEquals(VerificationOutcome.SUCCESS, reqToCompBWithResponse1.getEventOccurredModule().getVerificationOutcome()); 
	}

	/*
	 * @Override public void
	 * SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and
	 * () {
	 * 
	 * ServerReceivesRequestEvent reqToCompBWithResponse1 =
	 * defineRequestsToCompB("Response 1"); ServerReceivesRequestEvent
	 * reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");
	 * 
	 * //@formatter:off Story story = Story.given(storyLandscape)
	 * .when(and(reqToCompBWithResponse1,reqToCompBWithResponse2))
	 * .occurs_then_verify(reqToCompBWithResponse1) .and_the_story_continues();
	 * 
	 * story.logStorySoFar(); //@formatter:on
	 * 
	 * }
	 * 
	 * @Override public void
	 * SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or
	 * () { ServerReceivesRequestEvent reqToCompBWithResponse1 =
	 * defineRequestsToCompB("Response 1"); ServerReceivesRequestEvent
	 * reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");
	 * ComplexEvent complexEvent = or(reqToCompBWithResponse1,
	 * reqToCompBWithResponse2).getComplexEvent();
	 * 
	 * //@formatter:off Story story = Story.given(storyLandscape)
	 * .when(complexEvent) .occurs_then_verify(complexEvent)
	 * .and_the_story_continues();
	 * 
	 * story.logStorySoFar(); //@formatter:on
	 * 
	 * }
	 * 
	 * @Override public void
	 * SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or
	 * () {
	 * 
	 * ServerReceivesRequestEvent reqToCompBWithResponse1 =
	 * defineRequestsToCompB("Response 1"); ServerReceivesRequestEvent
	 * reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");
	 * ServerReceivesRequestEvent reqToCompBWithResponse3 =
	 * defineRequestsToCompB("Response 3");
	 * 
	 * ComplexEvent complexEvent = or(reqToCompBWithResponse1,
	 * and(reqToCompBWithResponse2, reqToCompBWithResponse3))
	 * .getComplexEvent();
	 * 
	 * //@formatter:off Story story = Story.given(storyLandscape)
	 * .when(complexEvent) .occurs_then_verify(complexEvent)
	 * .and_the_story_continues();
	 * 
	 * story.logStorySoFar(); //@formatter:on
	 * 
	 * }
	 */
}