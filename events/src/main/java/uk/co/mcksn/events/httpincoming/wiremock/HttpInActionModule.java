package uk.co.mcksn.events.httpincoming.wiremock;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import uk.co.mcksn.events.event.module.action.ActionModule;

public class HttpInActionModule implements ActionModule {

	private StubMapping stubMapping = null;
	private int times = 1;

	public void givenRequest(MappingBuilder mappingBuilder) {
		stubMapping = mappingBuilder.build();
	}

	public void givenRequest(MappingBuilder mappingBuilder, int times) {
		stubMapping = mappingBuilder.build();
		setTimes(times);

	}

	public StubMapping getStubMapping() {
		return stubMapping;
	}

	private void setTimes(int times) {
		if (times == 0) {
			throw new IllegalArgumentException("times cannot be null");
		}

		this.times = times;
	}

	public int getTimes() {
		return times;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
