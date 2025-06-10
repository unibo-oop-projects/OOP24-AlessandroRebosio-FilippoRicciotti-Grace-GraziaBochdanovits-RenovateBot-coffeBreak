package it.unibo.coffebreak.impl.view.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;

import javax.swing.JPanel;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.panel.Panel;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.api.view.states.ViewState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;
import it.unibo.coffebreak.impl.view.states.ingame.InGameView;
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
            final int panelWidth = super.getWidth();
            final int panelHeight = super.getHeight();
            final double targetRatio = 4.0 / 3.0;

            int drawWidth = panelWidth;
            int drawHeight = (int) (panelWidth / targetRatio);

            if (drawHeight > panelHeight) {
                drawHeight = panelHeight;
                drawWidth = (int) (panelHeight * targetRatio);
            }

            final int x = (panelWidth - drawWidth) / 2;
            final int y = (panelHeight - drawHeight) / 2;

            final Graphics2D g2d = (Graphics2D) g.create();

            g2d.setColor(super.getBackground());
            g2d.fillRect(0, 0, panelWidth, panelHeight);

            g2d.setClip(x, y, drawWidth, drawHeight);
            g2d.translate(x, y);
            this.currentViewState.draw(g2d, drawWidth, drawHeight);

            g2d.dispose();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateViewState(final Controller controller) {
        final ViewState nextState = switch (controller.getGameState()) {
            case final MenuModelState menu -> new MenuView(controller);
            case final InGameModelState inGame -> new InGameView(controller);
            default -> null;
        };

        if (nextState != null) {
            this.setViewState(nextState);
        }
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
