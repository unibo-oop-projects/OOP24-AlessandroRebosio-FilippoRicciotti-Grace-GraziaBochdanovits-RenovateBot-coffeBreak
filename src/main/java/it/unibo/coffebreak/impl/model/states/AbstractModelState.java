package it.unibo.coffebreak.impl.model.states;

import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.ModelState;

/**
 * Abstract base class for model states in the game.
 * <p>
 * Implements the {@link ModelState} interface and provides default (empty)
 * implementations for lifecycle and update methods. Subclasses must implement
 * command handling and option selection logic.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public abstract class AbstractModelState implements ModelState {

    /**
     * Called when entering this state. Default implementation does nothing.
     *
     * @param model the model entering this state
     */
    @Override
    public void onEnter(final Model model) {
        // Default empty implementation
    }

    /**
     * Called when exiting this state. Default implementation does nothing.
     *
     * @param model the model exiting this state
     */
    @Override
    public void onExit(final Model model) {
        // Default empty implementation
    }

    /**
     * Updates the state logic. Default implementation does nothing.
     *
     * @param model     the model being updated
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    @Override
    public void update(final Model model, final float deltaTime) {
        // Default empty implementation
    }

    /**
     * Handles input depending on the phase the model is currently in.
     *
     * @param model   the game model containing the possible phase to change
     * @param command the input to handle
     */
    @Override
    public abstract void handleCommand(Model model, Command command);

    /**
     * Returns the currently selected option for this state.
     *
     * @return the selected {@link Option}
     */
    @Override
    public abstract Option getSelectedOption();

    /**
     * Returns the list of available options for this state.
     *
     * @return the list of {@link Option}
     */
    @Override
    public abstract List<Option> getOptions();

}
