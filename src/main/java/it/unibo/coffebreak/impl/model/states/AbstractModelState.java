package it.unibo.coffebreak.impl.model.states;

import java.util.Collections;
import java.util.LinkedList;
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
     * The list of available menu options.
     */
    private final List<Option> options = new LinkedList<>();

    /**
     * The index of the currently selected option.
     */
    private int selectedIndex;

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
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case MOVE_UP -> {
                this.selectedIndex = (this.selectedIndex - 1 + this.options.size()) % options.size();
            }
            case MOVE_DOWN -> {
                this.selectedIndex = (this.selectedIndex + 1) % this.options.size();
            }
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option getSelectedOption() {
        return this.options.get(selectedIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Option> options() {
        return Collections.unmodifiableList(this.options);
    }

    /**
     * Adds the specified {@link Option} to the collection of options.
     *
     * @param option the option to be added
     * @return {@code true} if the option was added successfully, {@code false} if
     *         it was already present
     */
    protected boolean addOption(final Option option) {
        return this.options.add(option);
    }
}
