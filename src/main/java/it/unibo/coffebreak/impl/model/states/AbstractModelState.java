package it.unibo.coffebreak.impl.model.states;

import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.ModelState;

/**
 * An abstract base class representing a game State.
 * Implements basic functionality for game states, such as
 * handling Actions, exiting and entering States.
 * 
 * @author Filippo Ricciotti
 */
public abstract class AbstractModelState implements ModelState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnter(final Model model) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Model model, final float deltaTime) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void handleCommand(Model model, Command command);

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleDirection(final Model model, final Command command) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option getSelectedOption() {
        return Option.NONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Option> getOptions() {
        return List.of();
    }

}
