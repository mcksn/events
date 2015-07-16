package uk.co.mcksn.events.event.action;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import uk.co.mcksn.events.server.WireMockServerDef;

public class ServerReceivesRequestActionModule implements ActionModule {

	private StubMapping stubMapping = null;
	public void givenRequest(MappingBuilder mappingBuilder)
	{
		stubMapping = mappingBuilder.build();
	}

	public StubMapping getStubMapping() {
		return stubMapping;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}
