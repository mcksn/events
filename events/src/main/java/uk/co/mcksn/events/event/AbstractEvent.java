package uk.co.mcksn.events.event;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.mcksn.events.event.module.action.ActionModule;
import uk.co.mcksn.events.event.module.occured.OccurredModule;
import uk.co.mcksn.events.event.module.result.ResultModule;
import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;

public abstract class AbstractEvent<A extends ActionModule, R extends ResultModule, V extends VerificationPolicyModule> implements
		Event<A, R, V>{

	private String name = "No name defined";

	private OccurredModule occurredModule = new OccurredModule(this);

	public AbstractEvent() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public OccurredModule getOccurredModule() {
		return occurredModule;
	}

	@Override
	public String toString() {
		return "\n\n############# EVENT Name:" + name + " #####################\n\n"
				+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
				+ "\n\n############################################";
	}

}
