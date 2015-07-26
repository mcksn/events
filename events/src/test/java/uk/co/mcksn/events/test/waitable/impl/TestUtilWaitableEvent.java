package uk.co.mcksn.events.test.waitable.impl;

import uk.co.mcksn.events.event.AbstractEvent;
import uk.co.mcksn.events.event.module.tree.TreeModule;
import uk.co.mcksn.events.event.module.wait.WaitModule;
import uk.co.mcksn.events.event.type.Expectable;
import uk.co.mcksn.events.event.type.Whenable;

public class TestUtilWaitableEvent
		extends AbstractEvent<TestUtilWaitableActionModule, TestUtilWaitableResultModule, TestUtilWaitableVerificationPolicyModule>
		implements Expectable<TestUtilWaitableActionModule, TestUtilWaitableResultModule, TestUtilWaitableVerificationPolicyModule>,
		Whenable<TestUtilWaitableActionModule, TestUtilWaitableResultModule, TestUtilWaitableVerificationPolicyModule> {

	private TestUtilWaitableActionModule actionModule = new TestUtilWaitableActionModule();
	private TestUtilWaitableResultModule resultModule = new TestUtilWaitableResultModule();
	private TestUtilWaitableVerificationPolicyModule verificationPolicyModule = new TestUtilWaitableVerificationPolicyModule();

	private WaitModule waitModule = new WaitModule(this);
	private TreeModule treeModule = new TreeModule(this);

	public TestUtilWaitableEvent() {
		super();
	}

	public TestUtilWaitableActionModule getActionModule() {
		return actionModule;
	}

	public void setActionModule(TestUtilWaitableActionModule actionModule) {
		this.actionModule = actionModule;

	}

	public TestUtilWaitableResultModule getResultModule() {
		return resultModule;
	}

	public void setResultModule(TestUtilWaitableResultModule resultModule) {
		this.resultModule = resultModule;

	}

	public WaitModule getWaitModule() {
		return waitModule;
	}

	public TreeModule getTreeModule() {
		return treeModule;
	}

	public TestUtilWaitableVerificationPolicyModule getVerificationPolicyModule() {
		return verificationPolicyModule;
	}

	public void setVerificationPolicyModule(TestUtilWaitableVerificationPolicyModule verificationPolicyModule) {
		this.verificationPolicyModule = verificationPolicyModule;

	}

}
