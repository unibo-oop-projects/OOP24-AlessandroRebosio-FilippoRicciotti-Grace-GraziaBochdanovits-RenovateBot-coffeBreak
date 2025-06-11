package it.unibo.coffebreak.impl.view.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;

import javax.swing.JPanel;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.panel.Panel;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.api.view.states.ViewState;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverModelState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;
import it.unibo.coffebreak.impl.view.states.ingame.InGameView;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;
import it.unibo.coffebreak.impl.view.states.gameover.GameOverView;
import it.unibo.coffebreak.impl.view.states.menu.MenuView;
import it.unibo.coffebreak.impl.view.states.pause.PauseView;

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

    private static final int REF_WIDTH = 400;
    private static final int REF_HEIGHT = 640;

    @Serial
    private static final long serialVersionUID = 1L;
    private transient ViewState currentViewState;

    private float deltaTime;

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
        if (this.currentViewState != null) {
            final int panelWidth = getWidth();
            final int panelHeight = getHeight();

            final double scaleX = panelWidth / (double) REF_WIDTH;
            final double scaleY = panelHeight / (double) REF_HEIGHT;
            final double scale = Math.min(scaleX, scaleY);

            final int drawWidth = (int) (REF_WIDTH * scale);
            final int drawHeight = (int) (REF_HEIGHT * scale);
            final int x = (panelWidth - drawWidth) / 2;
            final int y = (panelHeight - drawHeight) / 2;

            final Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, panelWidth, panelHeight);

            g2d.translate(x, y);
            g2d.scale(scale, scale);

            this.currentViewState.draw(g2d, REF_WIDTH, REF_HEIGHT, deltaTime);

            g2d.dispose();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(REF_WIDTH, REF_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime, final Controller controller) {
        this.deltaTime = deltaTime;
        final ViewState nextState = switch (controller.getGameState()) {
            case final MenuModelState menu -> new MenuView(controller);
            case final InGameModelState inGame -> new InGameView(controller);
            case final PauseModelState pause -> new PauseView(controller);
            case final GameOverModelState gameOver -> new GameOverView(controller);
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
