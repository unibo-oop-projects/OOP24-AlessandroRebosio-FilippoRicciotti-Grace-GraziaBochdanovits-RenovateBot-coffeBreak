package it.unibo.coffebreak.api.model.states;

import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.common.State;
import it.unibo.coffebreak.api.model.Model;

/**
 * Represents a state of the game model.
 * <p>
 * Extends the generic {@link State} interface for {@link Model} and adds
 * support
 * for handling game commands.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public interface ModelState extends State<Model> {

    /**
     * Handles a command for the given model in this state.
     *
     * @param model   the model to handle the command for
     * @param command the command to process
     */
    void handleCommand(Model model, Command command);

    /**
     * Returns the currently selected option for this state, if any.
     *
     * @return the selected {@link Option}
     */
    Option getSelectedOption();

    /**
     * Returns an unmodifiable list of all available options.
     *
     * @return the list of {@link Option} available
     */
    List<Option> options();

}
