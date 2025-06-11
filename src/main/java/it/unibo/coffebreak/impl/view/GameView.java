package it.unibo.coffebreak.impl.view;

import javax.swing.JFrame;

import java.awt.event.WindowEvent;
import java.io.Serial;
import java.util.Objects;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.View;

/**
 * The main game view component.
 * 
 * <p>
 * This class extends {@link JFrame} and is responsible for managing the game
 * window.
 * Rendering and input handling are delegated to other components.
 * </p>
 * 
 * <p>
 * Part of the View in the MVC architecture.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameView extends JFrame implements View {

    private static final String TITLE = "Coffe Break";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a GameView with the given controller.
     *
     * @param controller the controller to notify of key events; must not be null
     * @throws NullPointerException if {@code controller} is null
     */
    public GameView(final Controller controller) {
        super(TITLE);
        Objects.requireNonNull(controller, "Controller cannot be null");

        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.pack();
        super.setLocationRelativeTo(null);
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
        this.repaint();
    }
}
