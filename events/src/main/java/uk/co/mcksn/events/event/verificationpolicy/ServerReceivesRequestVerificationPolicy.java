package uk.co.mcksn.events.event.verificationpolicy;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.github.tomakehurst.wiremock.client.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.matching.RequestPattern;

public class ServerReceivesRequestVerificationPolicy implements VerificationPolicy {

	private RequestPattern requestPattern;
	private boolean verifyAndContinueStory;

	public void verify(RequestPatternBuilder requestPatternBuilder) {
		requestPattern = requestPatternBuilder.build();
	}

	public RequestPattern getRequestPattern() {
		return requestPattern;
	}

	public void verifyAndContinueStory(boolean flag) {
		verifyAndContinueStory = flag;

	}

	public boolean verifyAndContinueStory() {
		return verifyAndContinueStory;
	}
	
	@Override
	public String toString() {
			return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}
