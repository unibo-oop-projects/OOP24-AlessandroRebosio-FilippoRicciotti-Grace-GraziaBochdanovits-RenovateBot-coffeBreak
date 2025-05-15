package it.unibo.coffebreak.view.impl;

import javax.swing.JPanel;
import it.unibo.coffebreak.controller.api.Controller;
import it.unibo.coffebreak.view.api.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;

/**
 * The main game view component.
 * 
 * <p>
 * This class extends {@link JPanel} and listens for keyboard input.
 * Key events are passed to the game {@link Controller} for processing.
 * </p>
 * 
 * <p>
 * Part of the View in the MVC architecture.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameView extends JPanel implements KeyListener, View {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Reference to the game controller. */
    private final transient Controller controller;

    /**
     * Constructs a GameView with the given controller.
     *
     * @param controller the controller to notify of key events
     */
    public GameView(final Controller controller) {
        this.controller = controller;
    }

    /**
     * Not used in this implementation.
     */
    @Override
    public void keyTyped(final KeyEvent e) {
        // Not used
    }

    /**
     * Sends the pressed key code to the controller.
     *
     * @param e the key event triggered when a key is pressed
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        this.controller.notifyInput(e.getKeyCode());
    }

    /**
     * Not used in this implementation.
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        // Not used
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
}
