package uk.co.mcksn.events.type;

import uk.co.mcksn.events.event.module.wait.AbstractWaitModule;
import uk.co.mcksn.events.tree.Treeable;

public interface Waitable extends Treeable {

	AbstractWaitModule getWaitModule();

}
