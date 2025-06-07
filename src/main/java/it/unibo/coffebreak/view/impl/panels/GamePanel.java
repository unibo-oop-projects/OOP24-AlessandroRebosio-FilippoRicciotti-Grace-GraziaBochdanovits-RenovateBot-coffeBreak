package it.unibo.coffebreak.view.impl.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;


import javax.swing.JPanel;

import it.unibo.coffebreak.api.view.panels.GameStatePanel;
import it.unibo.coffebreak.api.view.renders.RenderManager;

/**
 * A resizable panel for game rendering that uses double buffering to prevent flickering.
 * This panel automatically adjusts its buffer size when resized and delegates the rendering
 * of game elements to a {@link RenderManager}.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public final class GamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private transient GameStatePanel currentstateScreen;

    /**
     * Paints the component by first clearing the background and then delegating
     * the rendering to the render manager. Uses a buffer image to prevent flickering.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (currentstateScreen != null) {
            final Graphics2D g2d = (Graphics2D) g;
            currentstateScreen.render(g2d, getWidth(), getHeight());
        }
    }

    //TODO: fai una interfaccia
    /**
     * Sets the current game screen.
     * @param state the new state screen of the game
     */
    public void setCurrentState(final GameStatePanel state) {
        this.currentstateScreen = state;
        repaint();
    }
}
