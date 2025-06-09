package it.unibo.coffebreak.impl.view.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;

import javax.swing.JPanel;

import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.api.view.panel.Panel;
import it.unibo.coffebreak.api.view.renders.RenderManager;
import it.unibo.coffebreak.api.view.states.ViewState;
import it.unibo.coffebreak.impl.model.states.menu.MenuState;
import it.unibo.coffebreak.impl.view.states.menu.MenuView;

/**
 * A resizable panel for game rendering that uses double buffering to prevent
 * flickering.
 * This panel automatically adjusts its buffer size when resized and delegates
 * the rendering
 * of game elements to a {@link RenderManager}.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class GamePanel extends JPanel implements Panel {

    @Serial
    private static final long serialVersionUID = 1L;
    private transient ViewState currentViewState;

    /**
     * Paints the component by first clearing the background and then delegating
     * the rendering to the render manager. Uses a buffer image to prevent
     * flickering.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (currentViewState != null) {
            final Graphics2D g2d = (Graphics2D) g;
            currentViewState.draw(g2d, getWidth(), getHeight());
        }
    }

    /**
     * Update the current game screen.
     */
    @Override
    public void updateViewState(final ModelState modelState) {
        final ViewState nextState = switch (modelState) {
            case MenuState menu -> new MenuView();
            default -> null;
        };

        if (nextState != null) {
            this.setViewState(nextState);
        }

        super.repaint();
    }

    /**
     * Changes the current view state, calling onExit() and onEnter() properly.
     *
     * @param newView the new view state to activate
     */
    private void setViewState(final ViewState newView) {
        if (this.currentViewState != null) {
            this.currentViewState.onExit();
        }
        this.currentViewState = newView;
        this.currentViewState.onEnter();
    }
}
