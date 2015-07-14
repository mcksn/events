package uk.co.mcksn.events.event;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.action.ServerReceivesRequestAction;
import uk.co.mcksn.events.event.complex.ComplexEvent;
import uk.co.mcksn.events.event.module.occured.AbstractEventOccuredModule;
import uk.co.mcksn.events.event.module.occured.EventOccurredModule;
import uk.co.mcksn.events.event.module.tree.TreeEventModule;
import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.event.module.wait.WaitEventModule;
import uk.co.mcksn.events.event.result.ServerRecievesRequestResult;
import uk.co.mcksn.events.event.strategy.RegisterForWaitStrategyFactory;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.event.tree.EventTreeable;
import uk.co.mcksn.events.event.verificationpolicy.ServerReceivesRequestVerificationPolicy;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.WhenPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;
import uk.co.mcksn.events.server.WireMockServerDef;

public class ServerReceivesRequestEvent implements
		Event<ServerReceivesRequestAction, ServerRecievesRequestResult, ServerReceivesRequestVerificationPolicy>,
		ExpectPlotable, WhenPlotable {

	private ServerReceivesRequestAction action = new ServerReceivesRequestAction();
	private ServerRecievesRequestResult result = new ServerRecievesRequestResult();
	private ServerReceivesRequestVerificationPolicy verificationPolicy = new ServerReceivesRequestVerificationPolicy();
	private WaitEventModule waitEventModule = new WaitEventModule(this);
	private EventOccurredModule eventOccurredModule = new EventOccurredModule(this);
	protected TreeEventModule treeEventModule = new TreeEventModule(this);

	private WireMockServerDef wireMockServerDef;
	private String name = "Not defined";

	private EventTreeable parent = null;

	private EventState state = EventState.IN_PROGRESS;

	private VerificationOutcome verificationOutcome = VerificationOutcome.UNKOWN;

	public ServerReceivesRequestEvent() {
		super();
	}

	public ServerReceivesRequestEvent(WireMockServerDef wireMockServerDef) {
		super();
		this.wireMockServerDef = wireMockServerDef;
	}

	public void setParentsOfAllChildren(EventTreeable parentOfThisTree) {
		setParent(parentOfThisTree);

	}

	public EventState getUpdatedState() {
		return getState();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServerReceivesRequestAction getAction() {
		return action;
	}

	public void setAction(ServerReceivesRequestAction action) {
		this.action = action;

	}

	public ServerRecievesRequestResult getResult() {
		return result;
	}

	public void setResult(ServerRecievesRequestResult result) {
		this.result = result;

	}

	public ServerReceivesRequestVerificationPolicy getVerificationPolicy() {
		return verificationPolicy;
	}

	public void setVerificationPolicy(ServerReceivesRequestVerificationPolicy verificationPolicy) {
		this.verificationPolicy = verificationPolicy;

	}

	public WireMockServerDef getWireMockServerDef() {
		return wireMockServerDef;
	}

	public void setWireMockServerDef(WireMockServerDef wireMockServerDef) {
		this.wireMockServerDef = wireMockServerDef;
	}

	public EventTreeable getParent() {
		return parent;
	}

	public void setParent(EventTreeable parent) {
		this.parent = parent;
	}

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	public VerificationOutcome getVerificationOutcome() {
		return verificationOutcome;
	}

	public void setVerificationOutcome(VerificationOutcome verificationOutcome) {
		this.verificationOutcome = verificationOutcome;
	}

	@Override
	public AbstractWaitModule getWaitModule() {
		return waitEventModule;
	}

	public AbstractEventOccuredModule getEventOccurredModule() {
		return eventOccurredModule;
	}

	@Override
	public TreeEventModule getTreeModule() {
		return treeEventModule;
	}

	@Override
	public String toString() {
		return "\n\n############# EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

}
