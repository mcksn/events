package uk.co.mcksn.events.test.waitable.impl;

import static com.google.common.collect.Iterables.*;

import java.util.Collection;

import com.google.common.base.Predicate;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.stack.AbstractUpdateEventsStackWork;

@SuppressWarnings("rawtypes")
public class TestUtilWaitableUpdateStackWorkImpl extends AbstractUpdateEventsStackWork<TestUtilWaitableEvent> {

	private int watchableObjValue = 0;

	public TestUtilWaitableUpdateStackWorkImpl(int watchableObjChange) {
		this.watchableObjValue = watchableObjChange;
	}

	protected TestUtilWaitableEvent matchIncidentToEvent(Collection<Event> events) {
		return findMatchingEvent(events, watchableObjValue);
	}

	protected void updateResultModule(TestUtilWaitableEvent matchedEvent) {
		matchedEvent.getResultModule().setValue(watchableObjValue);
	}

	protected EventState getState(TestUtilWaitableEvent matchedEvent) {

		if (matchedEvent.getResultModule().getValue() == matchedEvent.getActionModule().getChange())
			return EventState.OCCURRED;
		else
			return EventState.IN_PROGRESS;

	}

	private static TestUtilWaitableEvent findMatchingEvent(Collection<Event> events, int change) {

		Event matchingEvent = find(events, watchableObjChangeMatchesEvent(change), null);

		if (matchingEvent == null) {
			return null;
		}

		return (TestUtilWaitableEvent) matchingEvent;

	}

	private static Predicate<Event> watchableObjChangeMatchesEvent(final int actualChange) {
		return new Predicate<Event>() {
			public boolean apply(Event event) {
				TestUtilWaitableEvent testUtilEvent = (TestUtilWaitableEvent) event;
				int eventExpectedChange = testUtilEvent.getActionModule().getChange();
				return eventExpectedChange == actualChange
						&& !event.getOccurredModule().getState().equals(EventState.OCCURRED);
			}

		};

	}
}
