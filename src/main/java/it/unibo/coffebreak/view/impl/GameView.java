package it.unibo.coffebreak.view.impl;

import javax.swing.JFrame;
import it.unibo.coffebreak.controller.api.Controller;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.Serial;
import java.util.Objects;

/**
 * The main game view component.
 * 
 * <p>
 * This class extends {@link JFrame} and listens for keyboard input.
 * Key events are passed to the game {@link Controller} for processing.
 * </p>
 * 
 * <p>
 * Part of the View in the MVC architecture.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameView extends JFrame implements KeyListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    /** Reference to the game controller. */
    private final transient Controller controller;

    /**
     * Constructs a GameView with the given controller.
     *
     * @param controller the controller to notify of key events
     */
    public GameView(final Controller controller) {
        this.controller = Objects.requireNonNull(controller, "Controller cannot be null");
        super.setTitle("Coffe Break");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(new Dimension(WIDTH, HEIGHT));
        super.setLocationRelativeTo(null);
        super.addKeyListener(this);
        super.setVisible(true);
    }

    /**
     * Programmatically closes the game window.
     * This method properly dispatches a window closing event.
     */
    public void closeGame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
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
        this.controller.handleKeyDown(e.getKeyCode());
    }

    /**
     * Not used in this implementation.
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        this.controller.handleKeyUp(e.getKeyCode());
    }
}
