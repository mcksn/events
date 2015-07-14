package uk.co.mcksn.events.plot;

import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.event.tree.EventTreeable;

public interface WaitPlotable extends EventTreeable {

	AbstractWaitModule getWaitModule();

}
