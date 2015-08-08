package uk.co.mcksn.events.test.waitable.impl;

import uk.co.mcksn.events.event.module.action.ActionModule;

public class TestUtilWaitableActionModule implements ActionModule {

	private Integer change = null;

	public void setChange(int change) {
		this.change = change;
	}

	public int getChange() {
		if (change == null)
			throw new RuntimeException("Change value of event must be set");
		return change;
	}

}
