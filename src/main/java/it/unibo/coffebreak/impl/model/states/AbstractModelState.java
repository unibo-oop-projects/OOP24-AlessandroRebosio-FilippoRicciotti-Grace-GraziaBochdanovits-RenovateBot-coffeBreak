package it.unibo.coffebreak.impl.model.states;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.controller.action.Action;
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
     * {@inheritDoc}
     * <p>
     * Provides default handling for navigation actions (UP/DOWN) to move through
     * menu options. Subclasses should override this method to handle additional
     * actions specific to their state.
     * </p>
     */
    @Override
    public void handleAction(final Model model, final Action action) {
        switch (action) {
            case UP -> {
                if (selectedIndex > 0) {
                    selectedIndex--;
                }
            }
            case DOWN -> {
                if (selectedIndex < options.size() - 1) {
                    selectedIndex++;
                }
            }
            default -> {
            }
        }
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
