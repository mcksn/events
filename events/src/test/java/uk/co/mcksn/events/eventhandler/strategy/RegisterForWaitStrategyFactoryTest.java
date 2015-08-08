package uk.co.mcksn.events.eventhandler.strategy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.eventstream.WaitHandlerable;

@RunWith(PowerMockRunner.class)
@SuppressWarnings("rawtypes")
@PrepareForTest(value = EventHandlerResolver.class)
public class RegisterForWaitStrategyFactoryTest {

	@Mock
	private List mockList;

	@Mock
	private Waitable mockWaitable;

	@Mock
	private WaitHandlerable mockWaitHandlerable;

	@InjectMocks
	private RegisterForWaitStrategyFactory factory = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(RegisterForWaitStrategyFactoryTest.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateRegisterForWaitStrategy() {

		// Given`
		RegisterForWaitStrategy expectedMockStrategy = mock(RegisterForWaitStrategy.class);

		given(mockWaitHandlerable.getRegisterForWaitStrategy()).willReturn(expectedMockStrategy);

		PowerMockito.mockStatic(EventHandlerResolver.class);

		given(EventHandlerResolver.findApplicableHandler(WaitHandlerable.class, mockWaitable, mockList))
				.willReturn(mockWaitHandlerable);

		// When
		RegisterForWaitStrategy actualStrategy = factory.createRegisterForWaitStrategy(mockWaitable);

		// Then
		assertThat(actualStrategy, is(equalTo(expectedMockStrategy)));
	}

}
