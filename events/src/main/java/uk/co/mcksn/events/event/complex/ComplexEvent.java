package uk.co.mcksn.events.event.complex;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.action.NoActionModule;
import uk.co.mcksn.events.event.module.occured.AbstractOccuredModule;
import uk.co.mcksn.events.event.module.result.NoResultModule;
import uk.co.mcksn.events.event.module.tree.AbstractTreeModule;
import uk.co.mcksn.events.event.module.tree.TreeComplexModule;
import uk.co.mcksn.events.event.module.vpolicy.NoVerificationPolicyModule;
import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.event.module.wait.WaitComplexModule;
import uk.co.mcksn.events.type.Waitable;

public abstract class ComplexEvent
		implements Event<NoActionModule, NoResultModule, NoVerificationPolicyModule>, Waitable {

	private String name = "Not defined";

	protected Collection<Event> children = new ArrayList<Event>();

	protected NoVerificationPolicyModule verificationPolicyModule = new NoVerificationPolicyModule();
	protected NoResultModule resultModule = new NoResultModule();
	protected NoActionModule actionModule = new NoActionModule();

	protected AbstractWaitModule<ComplexEvent> waitModule = new WaitComplexModule(this);
	protected AbstractOccuredModule<ComplexEvent> eventOccuredModule = null;
	protected AbstractTreeModule<ComplexEvent> treeEventModule = new TreeComplexModule(this);

	protected ComplexEvent() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NoActionModule getActionModule() {
		return actionModule;
	}

	public void setActionModule(NoActionModule actionModule) {
		this.actionModule = actionModule;
	}

	public NoResultModule getResultModule() {
		return resultModule;
	}

	public void setResultModule(NoResultModule resultModule) {
		this.resultModule = resultModule;
	}

	@Override
	public AbstractWaitModule getWaitModule() {
		return waitModule;
	}

	@Override
	public AbstractOccuredModule getEventOccurredModule() {
		return eventOccuredModule;
	}

	public AbstractTreeModule getTreeModule() {
		return treeEventModule;
	}

	@Override
	public NoVerificationPolicyModule getVerificationPolicyModule() {
		return verificationPolicyModule;
	}

	@Override
	public void setVerificationPolicyModule(NoVerificationPolicyModule verificationPolicyModule) {
		this.verificationPolicyModule = verificationPolicyModule;

	}

	public Collection<Event> getChildren() {
		return children;
	}

	public void setChildren(Collection<Event> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "\n\n############# COMPLEX EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

}
