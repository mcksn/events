package uk.co.mcksn.events.usage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

import uk.co.mcksn.events.blackbox.util.common.ExecutorServiceUtil;
import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.eventstream.EventHandlerable;
import uk.co.mcksn.events.eventstream.EventStream;
import uk.co.mcksn.events.test.waitable.impl.TestUtilWaitableEvent;
import uk.co.mcksn.events.test.waitable.impl.TestUtilWaitableEventHandler;

public class WhenUsageTest {
	
	@Test
	public void SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for() {

		TestUtilWaitableEvent testUtilWaitableEvent = new TestUtilWaitableEvent();
		testUtilWaitableEvent.getActionModule().setChange(0);

		final AtomicInteger watchableInt = new AtomicInteger(1);
		
		EventHandlerable eventHandler =  TestUtilWaitableEventHandler.looksLike(watchableInt);
		
		EventStream eventStream = EventStream.given(eventHandler);
		
		ExecutorServiceUtil.getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				watchableInt.set(0);
			}
		});
		

		eventStream.when(testUtilWaitableEvent);

		Assert.assertThat(testUtilWaitableEvent.getOccurredModule().getState(), is(equalTo(EventState.OCCURRED)));
		
		eventHandler.cleanUp();
	}
	
	

}
