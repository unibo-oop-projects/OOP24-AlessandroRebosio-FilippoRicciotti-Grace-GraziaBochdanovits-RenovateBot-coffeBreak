package it.unibo.coffebreak.impl.controller.input;

import java.awt.event.KeyEvent;
import java.util.Map;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.controller.input.Input;

/**
 * Concrete implementation of the {@link Input} interface.
 * <p>
 * Provides default key mappings for the game using immutable collections.
 * This implementation maps standard keyboard keys to corresponding game actions
 * for player input handling.
 * </p>
 * <p>
 * Default key mappings:
 * <ul>
 * <li>ENTER - Confirm/Select action</li>
 * <li>ESCAPE - Pause/Cancel action</li>
 * <li>UP/DOWN - Vertical movement/menu navigation</li>
 * <li>LEFT/RIGHT - Horizontal movement</li>
 * <li>SPACE - Jump action</li>
 * </ul>
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class InputManager implements Input {

    /**
     * {@inheritDoc}
     * <p>
     * Returns the default key mappings for the game.
     * The returned map is immutable and contains standard keyboard mappings
     * for game actions.
     * </p>
     * 
     * @return an immutable map of key codes to actions
     */
    @Override
    public Map<Integer, Action> getKeyMappings() {
        return Map.of(
                KeyEvent.VK_ENTER, Action.ENTER,
                KeyEvent.VK_ESCAPE, Action.ESCAPE,
                KeyEvent.VK_UP, Action.UP,
                KeyEvent.VK_DOWN, Action.DOWN,
                KeyEvent.VK_LEFT, Action.LEFT,
                KeyEvent.VK_RIGHT, Action.RIGHT,
                KeyEvent.VK_SPACE, Action.SPACE);
    }

}
