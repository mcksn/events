package uk.co.mcksn.events.event;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.action.ServerReceivesRequestAction;
import uk.co.mcksn.events.event.multi.ComplexEvent;
import uk.co.mcksn.events.event.multi.EventTreeable;
import uk.co.mcksn.events.event.result.ServerRecievesRequestResult;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.event.verificationpolicy.ServerReceivesRequestVerificationPolicy;
import uk.co.mcksn.events.plot.ExpectPlotable;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.WhenPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;
import uk.co.mcksn.events.server.WireMockServerDef;

public class ServerReceivesRequestEvent implements
		Event<ServerReceivesRequestAction, ServerRecievesRequestResult, ServerReceivesRequestVerificationPolicy>,
		EventTreeable, ExpectPlotable, WhenPlotable {

	private ServerReceivesRequestAction action = new ServerReceivesRequestAction();
	private ServerRecievesRequestResult result = new ServerRecievesRequestResult();
	private ServerReceivesRequestVerificationPolicy verificationPolicy = new ServerReceivesRequestVerificationPolicy();

	private WireMockServerDef wireMockServerDef;
	private String name = "Not defined";
	private Long timeout = 20000L;
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

	public ComplexEvent getComplexEvent() {
		return new ComplexEvent(this);
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

	public void doWait() {
		synchronized (this) {
			try {
				this.wait(timeout);
			} catch (InterruptedException e) {
				System.err.println(e);
				// TODO Log. Research how useful notifier pattern is. Would make
				// it difficult to debug
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

	@Override
	public VerificationOutcome getUpdatedVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {
		return getVerificationOutcome();
	}

}
