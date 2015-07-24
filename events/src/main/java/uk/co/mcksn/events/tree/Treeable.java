package uk.co.mcksn.events.tree;

import uk.co.mcksn.events.event.module.tree.AbstractTreeModule;

public interface Treeable {
	AbstractTreeModule<Treeable> getTreeModule();

}