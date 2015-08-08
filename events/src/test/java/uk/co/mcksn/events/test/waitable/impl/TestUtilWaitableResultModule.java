package uk.co.mcksn.events.test.waitable.impl;

import uk.co.mcksn.events.event.module.result.ResultModule;

public class TestUtilWaitableResultModule implements ResultModule {

	private Integer value = null;

	public int getValue() {
		if (value == null) {
			throw new RuntimeException("Result not set for event");
		}
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
