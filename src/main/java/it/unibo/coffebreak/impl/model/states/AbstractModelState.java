package it.unibo.coffebreak.impl.model.states;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.unibo.coffebreak.api.common.Option;
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
     * The index of the currently selected option.
     */
    private static final int SELECTEDINDEX = 0;

    /**
     * The list of available menu options.
     */
    private final List<Option> options = new LinkedList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Option getSelectedOption() {
        return this.options.get(SELECTEDINDEX);
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
