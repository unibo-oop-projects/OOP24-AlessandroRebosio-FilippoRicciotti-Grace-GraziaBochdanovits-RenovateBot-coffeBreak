package it.unibo.coffebreak.impl.view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.io.Serial;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.View;
import it.unibo.coffebreak.api.view.panel.Panel;
import it.unibo.coffebreak.impl.view.panel.GamePanel;

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

    /**
     * String containing the name of the Project.
     */
    public static final String TITLE = "Coffee Break";

    @Serial
    private static final long serialVersionUID = 1L;
    private final Panel gamePanel;

    /**
     * Constructs a GameView with the given controller.
     *
     * @param controller the controller to notify of key events
     * @param loader     the resource loader for graphics
     */
    public GameView(final Controller controller, final Loader loader) {
        super(TITLE);
        this.gamePanel = new GamePanel(controller, loader);

        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.add((GamePanel) gamePanel, BorderLayout.CENTER);
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
        this.gamePanel.update(deltaTime);
        this.repaint();
    }
}
