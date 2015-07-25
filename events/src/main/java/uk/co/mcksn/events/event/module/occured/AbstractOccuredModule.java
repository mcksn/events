package uk.co.mcksn.events.event.module.occured;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.mcksn.events.enumeration.EventState;
import uk.co.mcksn.events.enumeration.VerificationOutcome;
import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.eventhandler.strategy.VerificationStrategyFactory;
import uk.co.mcksn.events.type.Verifyable;

@SuppressWarnings("rawtypes")
public abstract class AbstractOccuredModule<VerifybleEvent extends Event & Verifyable> {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractOccuredModule.class);
	
	protected VerifybleEvent verifyPlotable = null;
	protected EventState state = EventState.IN_PROGRESS;
	protected VerificationOutcome verificationOutcome = VerificationOutcome.UNKOWN;
	protected VerificationStrategyFactory verificationStrategyFactory = null;

	public AbstractOccuredModule(VerifybleEvent verifyPlotable) {
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
