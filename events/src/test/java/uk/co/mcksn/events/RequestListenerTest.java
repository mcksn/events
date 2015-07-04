package uk.co.mcksn.events;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static uk.co.mcksn.events.event.multi.EventTreeBuilder.and;
import static uk.co.mcksn.events.event.multi.EventTreeBuilder.or;

import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import uk.co.mcksn.events.event.ServerReceivesRequestEvent;
import uk.co.mcksn.events.exception.VerificationFailedException;
import uk.co.mcksn.events.landscape.ServerReceivesRequestLandscape;
import uk.co.mcksn.events.server.WireMockServerDef;
import uk.co.mcksn.events.story.Story;

public class RequestListenerTest {

	public String LISTENER_URL = "/test/listener";
	public String LISTENER_RESPONSE_BDY = "Listener";
	public String LISTENER_RESPONSE_BDY_2 = "Listener2";

	public WireMockServerDef wireMockServerDefB = WireMockServerDef
			.buildDefintion(new WireMockServer(wireMockConfig().port(8090)), "Comp B");

	public WireMockServerDef wireMockServerDefC = WireMockServerDef
			.buildDefintion(new WireMockServer(wireMockConfig().port(8089)), "Comp C");

	private ServerReceivesRequestEvent defineBaseEventRequestsFromCompA() {

		ServerReceivesRequestEvent event = new ServerReceivesRequestEvent();
		event.getAction()
				.givenRequestFor(get(urlEqualTo(LISTENER_URL)).willReturn(aResponse().withBody(LISTENER_RESPONSE_BDY)));
		event.getVerificationPolicy().verifyAndContinueStory(true);
		event.setTimeout(20000L);
		return event;

	}

	private ServerReceivesRequestEvent defineRequestsFromCompAToCompB() {

		ServerReceivesRequestEvent event = defineBaseEventRequestsFromCompA();
		event.setName("Component A makes a request to Component B");
		event.setWireMockServerDef(wireMockServerDefB);
		event.getVerificationPolicy()
				.verify(getRequestedFor(urlEqualTo(LISTENER_URL)).withHeader("Head", equalTo("Header A to B")));
		return event;

	}

	private ServerReceivesRequestEvent defineRequestsFromCompAToCompC() {

		ServerReceivesRequestEvent event = defineBaseEventRequestsFromCompA();
		event.setName("Component A makes a request to Component C");
		event.setWireMockServerDef(wireMockServerDefC);
		event.getVerificationPolicy()
				.verify(getRequestedFor(urlEqualTo(LISTENER_URL)).withHeader("Head", equalTo("Header A to C")));
		return event;

	}

	@Test
	public void requestReceivedByListener() throws InterruptedException, VerificationFailedException {

		ServerReceivesRequestEvent reqFromCompAToCompB = defineRequestsFromCompAToCompB();
		ServerReceivesRequestEvent reqFromCompAToCompC = defineRequestsFromCompAToCompC();

		//@formatter:off
		Story story = 
		   Story.given(ServerReceivesRequestLandscape.looksLike(wireMockServerDefB, wireMockServerDefC))
				.when(or(reqFromCompAToCompC, and(reqFromCompAToCompB,reqFromCompAToCompB)).getComplexEvent())
/*				.and_when(reqFromCompAToCompC)
				.occurs_then_verify(reqFromCompAToCompC)*/
				.occurs_then_verify(reqFromCompAToCompB)
				.and_the_story_continues();

		   story.logStorySoFar();
		//@formatter:on

		wireMockServerDefB.getWireMockServer().stop();
		wireMockServerDefC.getWireMockServer().stop();

	}

}
