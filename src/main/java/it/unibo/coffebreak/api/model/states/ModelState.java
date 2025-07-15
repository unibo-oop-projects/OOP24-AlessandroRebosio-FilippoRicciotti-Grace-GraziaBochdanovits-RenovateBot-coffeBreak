package it.unibo.coffebreak.api.model.states;

import java.util.List;

import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.common.State;
import it.unibo.coffebreak.api.controller.action.Action;
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
     * Returns the currently selected option for this state, if any.
     *
     * @return the selected {@link Option}, or null if no valid option is selected
     */
    Option getSelectedOption();

    /**
     * Returns an unmodifiable list of all available options.
     *
     * @return the list of {@link Option} available
     */
    List<Option> options();

    /**
     * Handles an action within the context of this model state.
     * This method processes interface-related actions such as navigation,
     * confirmation, and cancellation that affect the state of the model.
     * 
     * @param model  the game model to be updated
     * @param action the action to be processed
     */
    void handleAction(Model model, Action action);

}
