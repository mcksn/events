package uk.co.mcksn.events.event.module.occured;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.event.EventState;
import uk.co.mcksn.events.event.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.plot.VerifyPlotable;
import uk.co.mcksn.events.plot.verify.VerificationOutcome;

public abstract class AbstractEventOccuredModule<VPlot extends VerifyPlotable> {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractEventOccuredModule.class);
	
	protected VPlot verifyPlotable = null;
	protected EventState state = EventState.IN_PROGRESS;
	protected VerificationOutcome verificationOutcome = VerificationOutcome.UNKOWN;
	protected VerificationStrategyFactory verificationStrategyFactory = null;

	public AbstractEventOccuredModule(VPlot verifyPlotable) {
		super();
		this.verifyPlotable = verifyPlotable;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	public void setVerificationOutcome(VerificationOutcome verificationOutcome) {
		this.verificationOutcome = verificationOutcome;
	}

	public abstract void setVerificationStrategyFactory(VerificationStrategyFactory verificationStrategyFactory);

	public EventState getState()
	{
		return getInternalState();
	}

	protected abstract EventState getInternalState();

	public VerificationOutcome getVerificationOutcome() {
		return internalGetVerificationOutcome();
	}

	protected abstract VerificationOutcome internalGetVerificationOutcome();

}
