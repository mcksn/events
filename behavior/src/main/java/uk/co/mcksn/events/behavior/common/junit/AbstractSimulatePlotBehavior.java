package uk.co.mcksn.events.behavior.common.junit;

public abstract class AbstractSimulatePlotBehavior {

	/*
	 * Scenario where you want to simulate a event.
	 */
	public abstract void SHOULD_simulate_event_WHEN_simulate_event_GIVEN_event_simulatable();

	/**
	 * Scenario where you want to test a simulated event occurred.
	 */
	public abstract void SHOULD_verify_event_WHEN_simulate_event_GIVEN_event_simulatable_and_verifiable();

	/**
	 * Scenario where you want to test an event occurred after you simulated an
	 * event and it completed.
	 */
	public abstract void SHOULD_verify_eventB_WHEN_simulate_eventA_then_verify_event_GIVEN_eventA_simulatable_eventB_verifiable();

	/**
	 * Scenario where you want to test event A occur after a component starts an
	 * event B. But, you may want to make this test before event B completes so
	 * you can prepare to expect a new event before event B completes.
	 * 
	 */
	public abstract void SHOULD_block_at_verify_before_event_complete_WHEN_simulate_event_asynchronously_then_verify_event_GIVEN_event_takes_a_while_to_complete();

	/**
	 * Scenario where you want to test event A occur after a component starts an
	 * event B. But, you may want to make this test before event B completes so
	 * you can the prepare to expect a new event before event B completes.
	 * 
	 * Simulated event may never occur so you want a way to continue the story
	 * it doesn't occur eventually.
	 * 
	 */
	public abstract void SHOULD_block_for_a_maximum_time_at_verify_before_event_complete_WHEN_simulate_event_asynchronously_then_verify_event_GIVEN_event_takes_more_than_maximum_time_to_complete();

	/**
	 * Scenario where you want to test events occurring after a simulated event
	 * is occurring.
	 * 
	 * Doesn't really make sense simulating asynchronously and verifying
	 * expected events occurred as you don't know when the async simulated event
	 * will complete. You would be better of using the when plot after the
	 * simulate to verify those events occurred.
	 */
	public abstract void SHOULD_verify_expected_events_after_simulate_WHEN_expect_eventB_then_simulate_eventB_then_verify_eventB_GIVEN_eventB_occurs_before_eventA_is_complete();

}
