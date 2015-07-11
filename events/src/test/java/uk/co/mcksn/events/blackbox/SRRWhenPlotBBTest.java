package uk.co.mcksn.events.blackbox;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static uk.co.mcksn.events.event.multi.EventTreeBuilder.and;
import static uk.co.mcksn.events.event.multi.EventTreeBuilder.or;

import java.io.IOException;

import static org.junit.Assert.*;
import static uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.mcksn.events.behavior.common.enumeration.AbstractWhenPlotBehaviorEnumeration;
import uk.co.mcksn.events.behavior.common.junit.AbstractWhenPlotBehavior;
import uk.co.mcksn.events.blackbox.util.apache.httpclient.ApacheHttpClientUtil;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.landscape.ServerReceivesRequestLandscape;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.story.AbstractStoryLandscape;
import uk.co.mcksn.events.story.Story;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Notifier;

public class SRRWhenPlotBBTest extends AbstractWhenPlotBehavior {

	private static final String COMPONENT_B_URL = "/compB";
	private static final String COMPONENT_C_URL = "/compC";

	private static String STUB_URL = "http://localhost:8080/stub-srr-when/invokeTest";

	private static final ApacheHttpClientUtil APACHE_HTTP_CLIENT = new ApacheHttpClientUtil(STUB_URL);

	private AbstractStoryLandscape<?> storyLandscape = null;

	private WireMockServerDef wireMockServerDefB = WireMockServerDef
			.buildDefintion(new WireMockServer(wireMockConfig().notifier(new Notifier() {

				@Override
				public void info(String message) {
					System.out.println("info " + message);

				}

				@Override
				public void error(String message, Throwable t) {
					System.out.println("error " + message + t);

				}

				@Override
				public void error(String message) {
					System.out.println("error " + message);

				}
			}).port(8051)), "Comp B");

	private WireMockServerDef wireMockServerDefC = WireMockServerDef
			.buildDefintion(new WireMockServer(wireMockConfig().port(8052)), "Comp C");

	private ServerReceivesRequestEvent defineBaseEventRequests(String requestBody, WireMockServerDef respondingServer,
			final String url, AbstractWhenPlotBehaviorEnumeration behaviorEnum) {

		ServerReceivesRequestEvent event = new ServerReceivesRequestEvent();
		event.getAction().givenRequest(get(urlEqualTo(url)).willReturn(aResponse().withBody("a response")));
		event.getVerificationPolicy().verifyAndContinueStory(false);
		event.setTimeout(20000L);
		event.setWireMockServerDef(respondingServer);
		event.getVerificationPolicy()
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
		try {
			APACHE_HTTP_CLIENT.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		//invoke test
		APACHE_HTTP_CLIENT.sendGet(behaviorEnum.name());

		// @formatter:off
		Story story = Story.given(storyLandscape);

		story.when(reqToCompBWithResponse1);

		story.verify(reqToCompBWithResponse1)
			 .and_the_story_continues();

		story.logStorySoFar();
		// @formatter:on

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