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
    public void enterPhase() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitPhase() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void handleAction(Command action, Model model);

}
