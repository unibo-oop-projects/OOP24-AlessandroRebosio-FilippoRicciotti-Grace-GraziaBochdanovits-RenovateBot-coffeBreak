package it.unibo.coffebreak.model.impl.phases;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;

/**
 * An abstract base class representing a game Phase.
 * Implements basic functionality for game Phases, such as
 * handling Actions, exiting and entering Phases.
 * 
 * @author Filippo Ricciotti
 */
public abstract class AbstractPhases implements Phases {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnter(final Model model) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Model model, final float deltaTime) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void handleCommand(Model model, Command command);

}
