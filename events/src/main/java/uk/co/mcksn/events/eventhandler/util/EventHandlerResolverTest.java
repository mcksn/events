package uk.co.mcksn.events.eventhandler.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

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

@RunWith(PowerMockRunner.class)
@SuppressWarnings("rawtypes")
@PrepareForTest(value = EventHandlerResolver.class)
public class EventHandlerResolverTest {

	@Mock
	private MockEvent mockEvent;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testFindApplicableHandler() {

		// Given
		MockEventHandler mockEventHandlerable = mock(MockEventHandler.class);

		doReturn(MockEvent.class).when(mockEventHandlerable).getEventType();
		List<? extends EventHandlerable> eventHandlerablesArg = Arrays.asList(mockEventHandlerable);

		// When
		MockEventHandler actualEventHandler = EventHandlerResolver.findApplicableHandler(MockEventHandler.class,
				mockEvent, eventHandlerablesArg);

		// Then
		assertThat(actualEventHandler, is(equalTo(mockEventHandlerable)));

	}

	private abstract class MockEvent extends AbstractEvent {
	}

	private abstract class MockEventHandler implements EventHandlerable {
	}

}
