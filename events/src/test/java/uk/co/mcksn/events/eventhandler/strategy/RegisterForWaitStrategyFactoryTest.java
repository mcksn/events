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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.co.mcksn.events.event.type.Waitable;
import uk.co.mcksn.events.eventhandler.util.EventHandlerResolver;
import uk.co.mcksn.events.eventstream.WaitHandlerable;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("rawtypes")
public class RegisterForWaitStrategyFactoryTest {

	
	@Mock
	private List mockList;

	@Mock
	private Waitable mockWaitable;

	@Mock
	private EventHandlerResolver mockEventHandlerResolver;

	@Mock
	private WaitHandlerable mockWaitHandlerable;

	@InjectMocks
	private RegisterForWaitStrategyFactory factory;

	@Before
	public void before()
	{
		factory.setEventHandlerResolver(mockEventHandlerResolver);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateRegisterForWaitStrategy() {
		
		// Given`
		RegisterForWaitStrategy expectedMockStrategy = mock(RegisterForWaitStrategy.class);

		given(mockEventHandlerResolver.findApplicableHandler(WaitHandlerable.class, mockWaitable, mockList))
		.willReturn(mockWaitHandlerable);

		given(mockWaitHandlerable.getRegisterForWaitStrategy()).willReturn(expectedMockStrategy);

		// When
		RegisterForWaitStrategy actualStrategy = factory.createRegisterForWaitStrategy(mockWaitable);

		// Then
		assertThat(actualStrategy, is(equalTo(expectedMockStrategy)));
	}

}
