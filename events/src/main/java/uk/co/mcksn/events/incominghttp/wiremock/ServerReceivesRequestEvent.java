package uk.co.mcksn.events.incominghttp.wiremock;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.occured.OccurredModule;
import uk.co.mcksn.events.event.module.tree.TreeModule;
import uk.co.mcksn.events.event.module.vpolicy.ServerReceivesRequestVerificationPolicyModule;
import uk.co.mcksn.events.event.module.wait.WaitModule;
import uk.co.mcksn.events.type.Expectable;
import uk.co.mcksn.events.type.Whenable;

public class ServerReceivesRequestEvent implements
		Event<ServerReceivesRequestActionModule, ServerRecievesRequestResultModule, ServerReceivesRequestVerificationPolicyModule>,
		Expectable, Whenable {

	private WireMockServerDef wireMockServerDef;
	private String name = "Not defined";
	
	private ServerReceivesRequestActionModule actionModule = new ServerReceivesRequestActionModule();
	private ServerRecievesRequestResultModule resultModule = new ServerRecievesRequestResultModule();
	private ServerReceivesRequestVerificationPolicyModule verificationPolicyModule = new ServerReceivesRequestVerificationPolicyModule();
	
	private WaitModule waitModule = new WaitModule(this);
	private OccurredModule occurredModule = new OccurredModule(this);
	private TreeModule treeModule = new TreeModule(this);


	public ServerReceivesRequestEvent() {
		super();
	}

	public ServerReceivesRequestEvent(WireMockServerDef wireMockServerDef) {
		super();
		this.wireMockServerDef = wireMockServerDef;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServerReceivesRequestActionModule getActionModule() {
		return actionModule;
	}

	public void setActionModule(ServerReceivesRequestActionModule actionModule) {
		this.actionModule = actionModule;

	}

	public ServerRecievesRequestResultModule getResultModule() {
		return resultModule;
	}

	public void setResultModule(ServerRecievesRequestResultModule resultModule) {
		this.resultModule = resultModule;

	}

	public WireMockServerDef getWireMockServerDef() {
		return wireMockServerDef;
	}

	public void setWireMockServerDef(WireMockServerDef wireMockServerDef) {
		this.wireMockServerDef = wireMockServerDef;
	}

	public WaitModule getWaitModule() {
		return waitModule;
	}

	public OccurredModule getOccurredModule() {
		return occurredModule;
	}

	public TreeModule getTreeModule() {
		return treeModule;
	}	

	public ServerReceivesRequestVerificationPolicyModule getVerificationPolicyModule() {
		return verificationPolicyModule;
	}

	public void setVerificationPolicyModule(ServerReceivesRequestVerificationPolicyModule verificationPolicyModule) {
		this.verificationPolicyModule = verificationPolicyModule;

	}

	@Override
	public String toString() {
		return "\n\n############# EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

}
