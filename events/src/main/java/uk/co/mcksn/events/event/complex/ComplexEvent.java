package uk.co.mcksn.events.event.complex;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.action.NoActionModule;
import uk.co.mcksn.events.event.module.occured.OccuredComplexModule;
import uk.co.mcksn.events.event.module.result.NoResultModule;
import uk.co.mcksn.events.event.module.tree.TreeComplexModule;
import uk.co.mcksn.events.event.module.vpolicy.NoVerificationPolicyModule;
import uk.co.mcksn.events.event.module.wait.WaitComplexModule;
import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.tree.Treeable;

@SuppressWarnings("rawtypes")
public abstract class ComplexEvent
		implements Waitable<NoActionModule, NoResultModule, NoVerificationPolicyModule> {

	private String name = "Not defined";

	private Collection<Treeable> children = new ArrayList<Treeable>();

	protected NoVerificationPolicyModule verificationPolicyModule = new NoVerificationPolicyModule();
	protected NoResultModule resultModule = new NoResultModule();
	protected NoActionModule actionModule = new NoActionModule();

	protected WaitComplexModule waitModule = new WaitComplexModule(this);
	protected OccuredComplexModule eventOccuredModule = null;
	protected TreeComplexModule treeEventModule = new TreeComplexModule(this);

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

	public WaitComplexModule getWaitModule() {
		return waitModule;
	}

	public OccuredComplexModule getOccurredModule() {
		return eventOccuredModule;
	}

	public TreeComplexModule getTreeModule() {
		return treeEventModule;
	}

	public NoVerificationPolicyModule getVerificationPolicyModule() {
		return verificationPolicyModule;
	}

	public void setVerificationPolicyModule(NoVerificationPolicyModule verificationPolicyModule) {
		this.verificationPolicyModule = verificationPolicyModule;

	}

	public  Collection<Treeable> getChildren() {
		return children;
	}

	public <T extends Event & Treeable> void addChildren(Collection<Treeable> children) {
		this.children.addAll(children);
	}

	@Override
	public String toString() {
		return "\n\n############# COMPLEX EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

}
