package uk.co.mcksn.events.event;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.action.ServerReceivesRequestAction;
import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventLeaf;
import uk.co.mcksn.events.event.multi.EventTree;
import uk.co.mcksn.events.event.result.ServerRecievesRequestResult;
import uk.co.mcksn.events.event.verificationpolicy.ServerReceivesRequestVerificationPolicy;
import uk.co.mcksn.events.server.WireMockServerDef;

public class ServerReceivesRequestEvent implements
		Event<ServerReceivesRequestAction, ServerRecievesRequestResult, ServerReceivesRequestVerificationPolicy>,
		EventLeaf {
	private ServerReceivesRequestAction action = new ServerReceivesRequestAction();
	private ServerRecievesRequestResult result = new ServerRecievesRequestResult();
	private ServerReceivesRequestVerificationPolicy verificationPolicy = new ServerReceivesRequestVerificationPolicy();

	private WireMockServerDef wireMockServerDef;
	private String name = "Not defined";
	private Long timeout = 20000L;
	private EventTree parent = null;

	private EventState state = EventState.IN_PROGRESS;

	public ServerReceivesRequestEvent() {
		super();
	}

	public ServerReceivesRequestEvent(WireMockServerDef wireMockServerDef) {
		super();
		this.wireMockServerDef = wireMockServerDef;
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

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void doWait() {
		synchronized (this) {
			try {
				this.wait(timeout);
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}

	}

	public void doNotify() {
		synchronized (this) {
			this.notify();

		}

	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public Long getTimeout() {
		return timeout;
	}

	@Override
	public String toString() {
		return "\n\n############# EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

	public ComplexEvent getComplexEvent() {
		return new ComplexEvent(this);
	}

	public EventTree getParent() {
		return parent;
	}

	public void setParent(EventTree parent) {
		this.parent = parent;
	}

	public void setParentsOfAllChildren(EventTree parentOfThisTree) {
		setParent(parentOfThisTree);

	}

	public EventState getUpdatedState() {
		return getState();
	}

}
