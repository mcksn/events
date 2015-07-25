package uk.co.mcksn.events.tree;

import uk.co.mcksn.events.event.Event;
import uk.co.mcksn.events.event.module.tree.AbstractTreeModule;

@SuppressWarnings("rawtypes")
public interface Treeable {

	<T extends Event & Treeable> AbstractTreeModule<T> getTreeModule();

}