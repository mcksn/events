package uk.co.mcksn.events.server;

import com.github.tomakehurst.wiremock.WireMockServer;

public class WireMockServerDef {

	private WireMockServer wireMockServer;
	private String name;

	public static WireMockServerDef buildDefintion(WireMockServer wireMockServer, String name) {
		return new WireMockServerDef(wireMockServer, name);
	}

	private WireMockServerDef(WireMockServer wireMockServer, String name) {
		super();
		this.wireMockServer = wireMockServer;
		this.name = name;
	}

	public WireMockServer getWireMockServer() {
		return wireMockServer;
	}

	public void setWireMockServer(WireMockServer wireMockServer) {
		this.wireMockServer = wireMockServer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
