package uk.co.mcksn.events.eventhandler.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.co.mcksn.events.event.AbstractEvent;
import uk.co.mcksn.events.eventstream.EventHandlerable;

@SuppressWarnings("rawtypes")
public class EventHandlerResolverTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testFindApplicableHandler() {

		// Given
		MockEventHandler mockEventHandlerable = mock(MockEventHandler.class);
		MockEvent mockEvent  = mock(MockEvent.class);

		doReturn(MockEvent.class).when(mockEventHandlerable).getEventType();
		List<EventHandlerable> eventHandlerablesArg = Arrays.asList((EventHandlerable) mockEventHandlerable);

		// When
		MockEventHandler actualEventHandler = new EventHandlerResolver().findApplicableHandler(MockEventHandler.class,
				mockEvent, eventHandlerablesArg);

		// Then
		assertThat(actualEventHandler, is(equalTo(mockEventHandlerable)));

	}

	private abstract class MockEvent extends AbstractEvent {
	}

	private abstract class MockEventHandler implements EventHandlerable {
	}

}
