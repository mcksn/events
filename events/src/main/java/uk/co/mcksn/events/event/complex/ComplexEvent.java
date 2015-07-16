package uk.co.mcksn.events.event.complex;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.action.NoActionModule;
import uk.co.mcksn.events.event.module.occured.AbstractEventOccuredModule;
import uk.co.mcksn.events.event.module.tree.AbstractTreeModule;
import uk.co.mcksn.events.event.module.tree.TreeComplextEventModule;
import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.event.module.wait.WaitComplexEventModule;
import uk.co.mcksn.events.event.result.NoResultModule;
import uk.co.mcksn.events.plot.WaitPlotable;
import uk.co.mcksn.events.plot.verify.NoVerficationPolicy;

public class ComplexEvent implements Event<NoActionModule, NoResultModule, NoVerficationPolicy>, WaitPlotable {

	private String name = "Not defined";
	protected Collection<Event> children = new ArrayList<Event>();
	
	protected NoVerficationPolicy verficationPolicy = new NoVerficationPolicy();
	protected NoResultModule resultModule = new NoResultModule();
	protected NoActionModule actionModule = new NoActionModule();

	protected AbstractWaitModule<ComplexEvent> waitModule = new WaitComplexEventModule(this);
	protected AbstractEventOccuredModule<ComplexEvent> eventOccuredModule = null;
	protected AbstractTreeModule<ComplexEvent> treeEventModule = new TreeComplextEventModule(this);


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
		return new NoActionModule();
	}

	public void setActionModule(NoActionModule actionModule) {
		this.actionModule = actionModule;
	}

	public NoResultModule getResultModule() {
		return new NoResultModule();
	}

	public void setResultModule(NoResultModule resultModule) {
		this.resultModule = resultModule;
	}

	@Override
	public AbstractWaitModule getWaitModule() {
		return waitModule;
	}

	@Override
	public AbstractEventOccuredModule getEventOccurredModule() {
		return eventOccuredModule;
	}

	public AbstractTreeModule getTreeModule() {
		return treeEventModule;
	}

	@Override
	public NoVerficationPolicy getVerificationPolicyModule() {
		return verficationPolicy;
	}

	@Override
	public void setVerificationPolicyModule(NoVerficationPolicy verificationPolicy) {
		this.verficationPolicy = verificationPolicy;

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
