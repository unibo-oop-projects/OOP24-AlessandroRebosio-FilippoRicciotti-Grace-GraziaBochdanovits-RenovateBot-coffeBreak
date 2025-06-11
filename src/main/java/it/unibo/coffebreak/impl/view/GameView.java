package it.unibo.coffebreak.impl.view;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.io.Serial;
import java.util.Objects;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.View;
import it.unibo.coffebreak.impl.view.panel.GamePanel;

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

    private static final float ASPECT_RATIO = 9f / 16f;

    @Serial
    private static final long serialVersionUID = 1L;

    private final transient Controller controller;
    private final GamePanel gamePanel;

    /**
     * Constructs a GameView with the given controller.
     *
     * @param controller the controller to notify of key events; must not be null
     * @throws NullPointerException if {@code controller} is null
     */
    public GameView(final Controller controller) {
        this.controller = Objects.requireNonNull(controller, "Controller cannot be null");

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = screenSize.height;
        final int width = (int) (height * ASPECT_RATIO);

        super.setTitle("Coffe Break");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(width, height);

        this.gamePanel = new GamePanel();
        super.setContentPane(gamePanel);
        super.setLocationRelativeTo(null);
        super.addKeyListener(this);
        super.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.gamePanel.update(deltaTime, this.controller);
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(final KeyEvent e) {
        // Not used
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        this.controller.keyPressed(e.getKeyCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        this.controller.keyReleased(e.getKeyCode());
    }
}
