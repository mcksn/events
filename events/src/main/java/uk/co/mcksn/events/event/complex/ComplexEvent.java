package uk.co.mcksn.events.event.complex;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.action.NoAction;
import uk.co.mcksn.events.event.module.occured.AbstractEventOccuredModule;
import uk.co.mcksn.events.event.module.tree.AbstractTreeModule;
import uk.co.mcksn.events.event.module.tree.TreeComplextEventModule;
import uk.co.mcksn.events.event.module.tree.TreeEventModule;
import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.event.module.wait.WaitComplexEventModule;
import uk.co.mcksn.events.event.result.NoResult;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.event.verificationpolicy.ComplextVerificationPolicy;
import uk.co.mcksn.events.plot.WaitPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public class ComplexEvent implements Event<NoAction, NoResult, ComplextVerificationPolicy>, WaitPlotable {

	private ComplextVerificationPolicy verificationPolicy;
	private String name = "Not defined";
	protected AbstractWaitModule<ComplexEvent> waitModule = new WaitComplexEventModule(this);
	protected AbstractEventOccuredModule<ComplexEvent> eventOccuredModule = null;
	protected AbstractTreeModule<ComplexEvent> treeEventModule = new TreeComplextEventModule(this);
	
	protected Collection<Event> leaves = new ArrayList<Event>();

	private EventState state = EventState.IN_PROGRESS;
	private VerificationOutcome verificationOutcome = VerificationOutcome.UNKOWN;

	protected ComplexEvent() {
		super();
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
	public AbstractWaitModule getWaitModule() {
		return waitModule;
	}

	@Override
	public AbstractEventOccuredModule getEventOccurredModule() {
		return eventOccuredModule;
	}

	public Collection<Event> getLeaves() {
		return leaves;
	}

	public void setLeaves(Collection<Event> leaves) {
		this.leaves = leaves;
	}
	@Override
	public AbstractTreeModule getTreeModule() {
		return treeEventModule;
	}

	@Override
	public String toString() {
		return "\n\n############# COMPLEX EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}



}
