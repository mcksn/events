package uk.co.mcksn.events.bb.common.srr;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static uk.co.mcksn.events.event.multi.EventTreeBuilder.and;
import static uk.co.mcksn.events.event.multi.EventTreeBuilder.or;

import org.junit.After;
import org.junit.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;

import uk.co.mcksn.events.bb.common.AbstractWhenPlotBBTest;
import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.landscape.ServerReceivesRequestLandscape;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.story.AbstractStoryLandscape;
import uk.co.mcksn.events.story.Story;

public class SRRWhenPlotBBTest extends AbstractWhenPlotBBTest {

	private static String LISTENER_URL = "/test/listener";
	private static String LISTENER_RESPONSE_BDY = "Listener";
	AbstractStoryLandscape<?> storyLandscape = null;

	private WireMockServerDef wireMockServerDefB = WireMockServerDef
			.buildDefintion(new WireMockServer(wireMockConfig().port(8090)), "Comp B");

	private WireMockServerDef wireMockServerDefC = WireMockServerDef
			.buildDefintion(new WireMockServer(wireMockConfig().port(8089)), "Comp C");

	private ServerReceivesRequestEvent defineBaseEventRequests(String requestBody, WireMockServerDef respondingServer) {

		ServerReceivesRequestEvent event = new ServerReceivesRequestEvent();
		event.getAction()
				.givenRequestFor(get(urlEqualTo(LISTENER_URL)).willReturn(aResponse().withBody(LISTENER_RESPONSE_BDY)));
		event.getVerificationPolicy().verifyAndContinueStory(true);
		event.setTimeout(20000L);
		event.setWireMockServerDef(respondingServer);
		event.getVerificationPolicy()
				.verify(getRequestedFor(urlEqualTo(LISTENER_URL)).withHeader("Head", equalTo("Header X")));
		return event;

	}

	private ServerReceivesRequestEvent defineRequestsToCompB(String requestBody) {

		ServerReceivesRequestEvent event = defineBaseEventRequests(requestBody, wireMockServerDefB);
		event.setName("Component A makes a request to Component B");
		event.setWireMockServerDef(wireMockServerDefB);
		return event;

	}

	private ServerReceivesRequestEvent defineRequestsToCompC(String requestBody) {

		ServerReceivesRequestEvent event = defineBaseEventRequests(requestBody, wireMockServerDefC);
		event.setName("Component A makes a request to Component C");
		event.setWireMockServerDef(wireMockServerDefC);

		return event;

	}

	@BeforeClass
	public void beforeClass() {
		AbstractStoryLandscape<?> storyLandscape = ServerReceivesRequestLandscape.looksLike(wireMockServerDefB,
				wireMockServerDefC);
	}

	@After
	public void after() {
		if (wireMockServerDefB.getWireMockServer().isRunning()) {
			wireMockServerDefB.getWireMockServer().stop();
		}
		if (wireMockServerDefB.getWireMockServer().isRunning()) {
			wireMockServerDefC.getWireMockServer().stop();
		}
	}

	@Override
	public void SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for() {

		ServerReceivesRequestEvent reqToCompBWithResponse1 = defineRequestsToCompB("Response 1");
		ServerReceivesRequestEvent reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");

		//@formatter:off
		Story story = 
		   Story.given(storyLandscape)
				.when(reqToCompBWithResponse1)
				.occurs_then_verify(reqToCompBWithResponse1)
				.and_the_story_continues();

		   story.logStorySoFar();
		//@formatter:on

	}

	@Override
	public void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and() {

		ServerReceivesRequestEvent reqToCompBWithResponse1 = defineRequestsToCompB("Response 1");
		ServerReceivesRequestEvent reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");

		//@formatter:off
		Story story = 
		   Story.given(storyLandscape)
				.when(and(reqToCompBWithResponse1,reqToCompBWithResponse2))
				.occurs_then_verify(reqToCompBWithResponse1)
				.and_the_story_continues();

		   story.logStorySoFar();
		//@formatter:on

	}

	@Override
	public void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or() {
		ServerReceivesRequestEvent reqToCompBWithResponse1 = defineRequestsToCompB("Response 1");
		ServerReceivesRequestEvent reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");
		ComplexEvent complexEvent = or(reqToCompBWithResponse1, reqToCompBWithResponse2).getComplexEvent();

		//@formatter:off
		Story story = 
		   Story.given(storyLandscape)
				.when(complexEvent)
				.occurs_then_verify(complexEvent)
				.and_the_story_continues();

		   story.logStorySoFar();
		//@formatter:on

	}

	@Override
	public void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or() {

		ServerReceivesRequestEvent reqToCompBWithResponse1 = defineRequestsToCompB("Response 1");
		ServerReceivesRequestEvent reqToCompBWithResponse2 = defineRequestsToCompB("Response 2");
		ServerReceivesRequestEvent reqToCompBWithResponse3 = defineRequestsToCompB("Response 3");

		ComplexEvent complexEvent = or(reqToCompBWithResponse1, and(reqToCompBWithResponse2, reqToCompBWithResponse3))
				.getComplexEvent();

		//@formatter:off
		Story story = 
		   Story.given(storyLandscape)
				.when(complexEvent)
				.occurs_then_verify(complexEvent)
				.and_the_story_continues();

		   story.logStorySoFar();
		//@formatter:on

	}

}
