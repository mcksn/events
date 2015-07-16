package uk.co.mcksn.events.event;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.action.ServerReceivesRequestActionModule;
import uk.co.mcksn.events.event.module.occured.AbstractEventOccuredModule;
import uk.co.mcksn.events.event.module.occured.EventOccurredModule;
import uk.co.mcksn.events.event.module.tree.TreeEventModule;
import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.event.module.wait.WaitEventModule;
import uk.co.mcksn.events.event.result.ServerRecievesRequestResultModule;
import uk.co.mcksn.events.event.verificationpolicy.ServerReceivesRequestVerificationPolicyModule;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.WhenPlotable;
import uk.co.mcksn.events.server.WireMockServerDef;

public class ServerReceivesRequestEvent implements
		Event<ServerReceivesRequestActionModule, ServerRecievesRequestResultModule, ServerReceivesRequestVerificationPolicyModule>,
		ExpectPlotable, WhenPlotable {

	private WireMockServerDef wireMockServerDef;
	private String name = "Not defined";
	
	private ServerReceivesRequestActionModule actionModule = new ServerReceivesRequestActionModule();
	private ServerRecievesRequestResultModule resultModule = new ServerRecievesRequestResultModule();
	private ServerReceivesRequestVerificationPolicyModule verificationPolicyModule = new ServerReceivesRequestVerificationPolicyModule();
	
	private WaitEventModule waitEventModule = new WaitEventModule(this);
	private EventOccurredModule eventOccurredModule = new EventOccurredModule(this);
	private TreeEventModule treeEventModule = new TreeEventModule(this);



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

	@Override
	public AbstractWaitModule getWaitModule() {
		return waitEventModule;
	}

	@Override
	public AbstractEventOccuredModule getEventOccurredModule() {
		return eventOccurredModule;
	}

	@Override
	public TreeEventModule getTreeModule() {
		return treeEventModule;
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
