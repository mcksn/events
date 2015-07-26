package uk.co.mcksn.events.httpincoming.wiremock;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.github.tomakehurst.wiremock.client.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.matching.RequestPattern;

import uk.co.mcksn.events.event.module.vpolicy.VerificationPolicyModule;

public class HttpInVerificationPolicyModule implements VerificationPolicyModule {

	private RequestPattern requestPattern;

	public void set(RequestPatternBuilder requestPatternBuilder) {
		requestPattern = requestPatternBuilder.build();
	}
	
	public RequestPattern getRequestPattern() {
		return requestPattern;
	}

	
	@Override
	public String toString() {
			return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}
