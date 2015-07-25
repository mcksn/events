package uk.co.mcksn.events.httpincoming.wiremock;

import uk.co.mcksn.events.event.AbstractEvent;
import uk.co.mcksn.events.event.module.tree.TreeModule;
import uk.co.mcksn.events.event.module.wait.WaitModule;
import uk.co.mcksn.events.event.type.Expectable;
import uk.co.mcksn.events.event.type.Whenable;

public class HttpInEvent extends AbstractEvent<HttpInActionModule, HttpInResultModule, HttpInVerificationPolicyModule>
		implements Expectable<HttpInActionModule, HttpInResultModule, HttpInVerificationPolicyModule>,
		Whenable<HttpInActionModule, HttpInResultModule, HttpInVerificationPolicyModule> {

	private WireMockServerDef wireMockServerDef;

	private HttpInActionModule actionModule = new HttpInActionModule();
	private HttpInResultModule resultModule = new HttpInResultModule();
	private HttpInVerificationPolicyModule verificationPolicyModule = new HttpInVerificationPolicyModule();

	private WaitModule waitModule = new WaitModule(this);
	private TreeModule treeModule = new TreeModule(this);

	public HttpInEvent() {
		super();
	}

	public HttpInEvent(WireMockServerDef wireMockServerDef) {
		super();
		this.wireMockServerDef = wireMockServerDef;
	}

	public HttpInActionModule getActionModule() {
		return actionModule;
	}

	public void setActionModule(HttpInActionModule actionModule) {
		this.actionModule = actionModule;

	}

	public HttpInResultModule getResultModule() {
		return resultModule;
	}

	public void setResultModule(HttpInResultModule resultModule) {
		this.resultModule = resultModule;

	}

	public WireMockServerDef getWireMockServerDef() {
		return wireMockServerDef;
	}

	public void setWireMockServerDef(WireMockServerDef wireMockServerDef) {
		this.wireMockServerDef = wireMockServerDef;
	}

	public WaitModule getWaitModule() {
		return waitModule;
	}

	public TreeModule getTreeModule() {
		return treeModule;
	}

	public HttpInVerificationPolicyModule getVerificationPolicyModule() {
		return verificationPolicyModule;
	}

	public void setVerificationPolicyModule(HttpInVerificationPolicyModule verificationPolicyModule) {
		this.verificationPolicyModule = verificationPolicyModule;

	}

}
