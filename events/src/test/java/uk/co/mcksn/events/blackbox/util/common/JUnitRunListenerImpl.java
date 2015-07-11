package uk.co.mcksn.events.blackbox.util.common;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class JUnitRunListenerImpl extends RunListener {

	private static final String NEW_LINE = "\n";
	private static final String DECORATER = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";

	@Override
	public void testStarted(Description description) throws Exception {

		decorateStart();
		System.out.println("Running: " + getBehavior(description));
		decorateEnd();
	}

	@Override
	public void testFinished(Description description) throws Exception {
		decorateStart();
		System.out.println("Finished: " + getBehavior(description));
		decorateEnd();
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		decorateStart();
		System.out.println("Failed: " + getBehavior(failure.getDescription()));
		decorateEnd();
	}

	@Override
	public void testIgnored(Description description) throws Exception {
		decorateStart();
		System.out.println("Ignoring: " + getBehavior(description));
		decorateEnd();
	}

	private String getBehavior(Description description) {
		return description.getMethodName().replace("_", " ");
	}

	private void decorateStart() {
		System.out.println(NEW_LINE + DECORATER);
	}

	private void decorateEnd() {
		System.out.println(DECORATER + NEW_LINE);
	}
}
