package uk.co.mcksn.events.event.multi;

import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.WaitPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public interface EventTreeable extends WaitPlotable, VerifyPlotable {

	ComplexEvent getComplexEvent();

	EventTreeable getParent();

	void setParent(EventTreeable parent);

	void setParentsOfAllChildren(EventTreeable rootParent);

	EventState getUpdatedState();

	VerificationOutcome getUpdatedVerificationOutcome(VerificationStrategyFactory verificationStrategyFactory);

}