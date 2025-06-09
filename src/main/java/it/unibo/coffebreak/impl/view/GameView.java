package it.unibo.coffebreak.impl.view;

import javax.swing.JFrame;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.View;
import it.unibo.coffebreak.impl.view.panels.GameOverPanel;
import it.unibo.coffebreak.impl.view.panels.GamePanel;
import it.unibo.coffebreak.impl.view.panels.InGamePanel;
import it.unibo.coffebreak.impl.view.panels.MenuPanel;
import it.unibo.coffebreak.impl.view.panels.PausePanel;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
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
public class GameView extends JFrame implements View {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    /** Reference to the game controller. */
    private final transient Controller controller;
    private final GamePanel gamePanel;

    /**
     * Constructs a GameView with the given controller.
     *
     * @param controller the controller to notify of key events
     */
    public GameView(final Controller controller) {
        this.controller = Objects.requireNonNull(controller, "Controller cannot be null");

        super.setTitle("Coffe Break");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        this.gamePanel = new GamePanel();
        //controller.getModel().setState(PauseState::new);
        //gamePanel.setCurrentState(new PausePanel(new ResourceLoader(), controller));
        super.setContentPane(gamePanel);
        super.setLocationRelativeTo(null);
        super.addKeyListener(this);
        super.setVisible(true);
        this.setStatePanel(controller.getGameState().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Sets current panel to the one the model switched to.
     * 
     * @param panel Panel to switch to
     */
    @Override
    public final void setStatePanel(final String panel) { // TODO: consider enum StateType
        switch (panel) {
            case "MENU" -> gamePanel.setCurrentState(
                    new MenuPanel(new ResourceLoader(), controller));
            case "PAUSE" -> gamePanel.setCurrentState(
                    new PausePanel(new ResourceLoader(), controller));
            case "GAME OVER" -> gamePanel.setCurrentState(
                    new GameOverPanel(new ResourceLoader(), controller));
            case "IN GAME" -> gamePanel.setCurrentState(
                    new InGamePanel(controller, DEFAULT_WIDTH, DEFAULT_HEIGHT));
            default -> {
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateView() {
        this.repaint();
        getContentPane().repaint();
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
        this.controller.keyPressed(e.getKeyCode());
    }

    /**
     * Not used in this implementation.
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        this.controller.keyReleased(e.getKeyCode());
    }
}
