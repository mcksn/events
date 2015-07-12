package uk.co.mcksn.events.event.multi;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.action.NoAction;
import uk.co.mcksn.events.event.result.NoResult;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.event.verificationpolicy.ComplextVerificationPolicy;
import uk.co.mcksn.events.plot.WaitPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public class ComplexEvent implements Event<NoAction, NoResult, ComplextVerificationPolicy>, WaitPlotable {

	private ComplextVerificationPolicy verificationPolicy;

	private EventTreeable eventTree;
	private String name = "Not defined";
	private Long timeout = 20000L;

	private EventState state = EventState.IN_PROGRESS;
	private VerificationOutcome verificationOutcome = VerificationOutcome.UNKOWN;

	public ComplexEvent(EventTreeable eventTree) {
		this.eventTree = eventTree;
	}


	public void setParentsOfAllChildren(EventTreeable rootParent) {
		setParent(rootParent);
		this.eventTree.setParentsOfAllChildren(rootParent);
	}
	


	public EventState getUpdatedState() {
		if (state.equals(EventState.OCCURRED)) {
			return state;
		}
		state = eventTree.getUpdatedState();

		return state;
	}

	public VerificationOutcome getUpdatedVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory) {
		if (!verificationOutcome.equals(VerificationOutcome.UNKOWN)) {
			return verificationOutcome;
		}

		verificationOutcome = eventTree.getUpdatedVerificationOutcome(verificationStrategyFactory);
		return verificationOutcome;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NoAction getAction() {
		return new NoAction();
	}

	public void setAction(NoAction action) {
		// do nothing
	}

	public NoResult getResult() {
		return new NoResult();
	}

	public void setResult(NoResult result) {
		// do nothing
	}

	public ComplextVerificationPolicy getVerificationPolicy() {
		return verificationPolicy;
	}

	public void setVerificationPolicy(ComplextVerificationPolicy verificationPolicy) {
		this.verificationPolicy = verificationPolicy;

	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public Long getTimeout() {
		return timeout;
	}

	public ComplexEvent getComplexEvent() {
		return this;
	}

	public EventTreeable getParent() {
		return eventTree.getParent();
	}

	public void setParent(EventTreeable parent) {
		this.eventTree.setParent(parent);
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

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	@Override
	public VerificationOutcome getVerificationOutcome() {
		return verificationOutcome;
	}

	@Override
	public void setVerificationOutcome(VerificationOutcome verificationOutcome) {
		this.verificationOutcome = verificationOutcome;
	}

	@Override
	public String toString() {
		return "\n\n############# COMPLEX EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

}
