package uk.co.mcksn.events.event.multi;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.action.NoAction;
import uk.co.mcksn.events.event.result.NoResult;
import uk.co.mcksn.events.event.verificationpolicy.ComplextVerificationPolicy;

public class ComplexEvent implements Event<NoAction, NoResult, ComplextVerificationPolicy>, EventTree {

	private ComplextVerificationPolicy verificationPolicy;

	private EventTree eventTree;
	private String name = "Not defined";
	private Long timeout = 20000L;

	private EventState state = EventState.IN_PROGRESS;

	public ComplexEvent(EventTree eventTree) {
		this.eventTree = eventTree;
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
		return "\n\n############# COMPLEX EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

	public ComplexEvent getComplexEvent() {
		return this;
	}

	public EventTree getParent() {
		return eventTree.getParent();
	}

	public void setParent(EventTree parent) {
		this.eventTree.setParent(parent);
	}

	public void setParentsOfAllChildren(EventTree rootParent) {
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

}
