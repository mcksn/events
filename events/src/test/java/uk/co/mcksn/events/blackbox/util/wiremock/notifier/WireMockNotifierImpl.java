package uk.co.mcksn.events.blackbox.util.wiremock.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.common.Notifier;

public class WireMockNotifierImpl implements Notifier {

	private static final Logger LOGGER = LoggerFactory.getLogger(WireMockNotifierImpl.class);

	@Override
	public void info(String message) {
		//System.out.println(message);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}

	}

	@Override
	public void error(String message) {
		//System.err.println(message);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}

	}

	@Override
	public void error(String message, Throwable t) {
		//System.err.println(message + " " + t);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message, t);
		}

	}

}
