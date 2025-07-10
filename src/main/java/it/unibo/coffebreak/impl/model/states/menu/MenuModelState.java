package it.unibo.coffebreak.impl.model.states.menu;


import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;

/**
 * State representing the main menu of the game.
 * <p>
 * Handles option selection and command processing for menu navigation and
 * actions.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public class MenuModelState extends AbstractModelState {

    /**
     * Constructs a new MenuModelState and initializes the available menu options.
     * Adds the START and QUIT options to the menu.
     */
    public MenuModelState() {
        super.addOption(Option.START);
        super.addOption(Option.QUIT);
    }
}
