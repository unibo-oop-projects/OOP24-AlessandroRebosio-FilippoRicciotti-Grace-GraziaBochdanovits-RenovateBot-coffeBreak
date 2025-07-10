package it.unibo.coffebreak.impl.model.states.pause;

import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;

/**
 * State representing the pause menu of the game.
 * <p>
 * Handles option selection and command processing for pause menu navigation and
 * actions.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public class PauseModelState extends AbstractModelState {

    /**
     * Constructs a new PauseModelState and initializes the available menu options.
     * Adds the RRESUME, MENU and QUIT options to the menu.
     */
    public PauseModelState() {
        super.addOption(Option.RESUME);
        super.addOption(Option.MENU);
        super.addOption(Option.QUIT);
    }

}
