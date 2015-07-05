package uk.co.mcksn.events.bb.common;

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


	@Test
	public void requestReceivedByListener() throws InterruptedException, VerificationFailedException {



	}

}
