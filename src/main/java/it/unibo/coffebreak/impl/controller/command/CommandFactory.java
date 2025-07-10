package it.unibo.coffebreak.impl.controller.command;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.controller.command.Command;
import it.unibo.coffebreak.impl.controller.command.movement.JumpCommand;
import it.unibo.coffebreak.impl.controller.command.movement.MoveDownCommand;
import it.unibo.coffebreak.impl.controller.command.movement.MoveLeftCommand;
import it.unibo.coffebreak.impl.controller.command.movement.MoveRightCommand;
import it.unibo.coffebreak.impl.controller.command.movement.MoveUpCommand;
import it.unibo.coffebreak.impl.controller.command.movement.StopHorizontalMovementCommand;
import it.unibo.coffebreak.impl.controller.command.movement.StopVerticalMovementCommand;
import it.unibo.coffebreak.impl.controller.command.system.ContextualCommand;
import it.unibo.coffebreak.impl.controller.command.system.NavigationCommand;
import it.unibo.coffebreak.impl.controller.command.system.NoCommand;

/**
 * Factory class for creating game commands based on key events.
 * Uses the Factory Pattern to encapsulate command creation logic.
 * 
 * @author Alessandro Rebosio
 */
public final class CommandFactory {

    private static final Map<Integer, Command> KEY_PRESS_COMMANDS = new HashMap<>();
    private static final Map<Integer, Command> KEY_RELEASE_COMMANDS = new HashMap<>();
    private static final Command NO_COMMAND = new NoCommand();

    static {
        KEY_PRESS_COMMANDS.put(KeyEvent.VK_LEFT, new MoveLeftCommand());
        KEY_PRESS_COMMANDS.put(KeyEvent.VK_RIGHT, new MoveRightCommand());
        KEY_PRESS_COMMANDS.put(KeyEvent.VK_SPACE, new JumpCommand());
        KEY_PRESS_COMMANDS.put(KeyEvent.VK_ENTER, new NavigationCommand(Action.ENTER));
        KEY_PRESS_COMMANDS.put(KeyEvent.VK_ESCAPE, new NavigationCommand(Action.ESCAPE));
        KEY_PRESS_COMMANDS.put(KeyEvent.VK_UP, createContextualUpCommand());
        KEY_PRESS_COMMANDS.put(KeyEvent.VK_DOWN, createContextualDownCommand());

        KEY_RELEASE_COMMANDS.put(KeyEvent.VK_LEFT, new StopHorizontalMovementCommand());
        KEY_RELEASE_COMMANDS.put(KeyEvent.VK_RIGHT, new StopHorizontalMovementCommand());
        KEY_RELEASE_COMMANDS.put(KeyEvent.VK_UP, createContextualUpStopCommand());
        KEY_RELEASE_COMMANDS.put(KeyEvent.VK_DOWN, createContextualDownStopCommand());
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private CommandFactory() {
        // Utility class
    }

    /**
     * Creates a command for the given key press event.
     * 
     * @param keyCode the key code from KeyEvent
     * @return the appropriate command, or NoCommand if key is not mapped
     */
    public static Command createCommand(final int keyCode) {
        return KEY_PRESS_COMMANDS.getOrDefault(keyCode, NO_COMMAND);
    }

    /**
     * Creates a command for the given key release event.
     * 
     * @param keyCode the key code from KeyEvent
     * @return the appropriate command, or NoCommand if key is not mapped
     */
    public static Command createReleaseCommand(final int keyCode) {
        return KEY_RELEASE_COMMANDS.getOrDefault(keyCode, NO_COMMAND);
    }

    /**
     * Creates a contextual UP command that handles both navigation and climbing.
     * 
     * @return a contextual command for UP key handling
     */
    private static Command createContextualUpCommand() {
        return new ContextualCommand(
                new NavigationCommand(Action.UP),
                new MoveUpCommand());
    }

    /**
     * Creates a contextual DOWN command that handles both navigation and climbing.
     * 
     * @return a contextual command for DOWN key handling
     */
    private static Command createContextualDownCommand() {
        return new ContextualCommand(
                new NavigationCommand(Action.DOWN),
                new MoveDownCommand());
    }

    /**
     * Creates a contextual UP stop command for stopping vertical movement.
     * 
     * @return a command for stopping vertical movement when UP key is released
     */
    private static Command createContextualUpStopCommand() {
        return new StopVerticalMovementCommand();
    }

    /**
     * Creates a contextual DOWN stop command for stopping vertical movement.
     * 
     * @return a command for stopping vertical movement when DOWN key is released
     */
    private static Command createContextualDownStopCommand() {
        return new StopVerticalMovementCommand();
    }
}
