package uk.co.mcksn.events.bb.common;

import org.junit.Test;

public abstract class AbstractWhenPlotBBTest {

	/*
	 * Scenario where you want to simulate a event.
	 */
	@Test
	public abstract void SHOULD_wait_for_event_to_occur_WHEN_when_event_GIVEN_event_can_be_waited_for();
	
	/*
	 *
	 */
	@Test
	public abstract void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and();
	
	/*
	 *
	 */
	@Test
	public abstract void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_or();
	
	/*
	 *
	 */
	@Test
	public abstract void SHOULD_wait_for_multiple_events_to_occur_WHEN_when_events_verify_all_events_GIVEN_events_consist_of_multiple_and_with_or();
}
