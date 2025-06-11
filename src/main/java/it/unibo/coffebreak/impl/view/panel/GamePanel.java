package it.unibo.coffebreak.impl.view.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serial;
import java.util.Objects;

import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.panel.Panel;
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
 * Resizable panel for game rendering, using double buffering to prevent
 * flickering.
 * This panel automatically adjusts the rendering area size to maintain a 10:16
 * aspect ratio
 * and delegates drawing of game elements to the current view state.
 * Also handles keyboard input via KeyAdapter.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class GamePanel extends JPanel implements Panel {

    private static final int REF_WIDTH = 600;
    private static final int REF_HEIGHT = 800;
    private static final double ASPECT_RATIO = 0.75;

    @Serial
    private static final long serialVersionUID = 1L;
    private transient ViewState currentViewState;
    private final transient Controller controller;

    private float deltaTime;

    /**
     * Constructs a GamePanel associated with the given controller and sets up the
     * KeyAdapter.
     *
     * @param controller the controller to notify for input events; must not be null
     */
    public GamePanel(final Controller controller) {
        super();
        this.controller = Objects.requireNonNull(controller, "Il controller non può essere null");

        super.setFocusable(true);

        super.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                controller.keyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                controller.keyReleased(e.getKeyCode());
            }
        });

        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final Rectangle bounds = getBounds(); //TODO: it takes all window size, i need only panel size
                controller.updateGameBounds(bounds.width, bounds.height);
            }
        });
        super.requestFocusInWindow();
    }

    /**
     * Paints the component by centering and scaling the rendering area to maintain
     * a 10:16 aspect ratio.
     * Delegates rendering to the current view state.
     *
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        if (this.currentViewState == null) {
            return;
        }

        final int panelWidth = getWidth();
        final int panelHeight = getHeight();
        final double panelRatio = (double) panelWidth / panelHeight;

        final int renderWidth, renderHeight;
        if (panelRatio > ASPECT_RATIO) {
            renderHeight = panelHeight;
            renderWidth = (int) (panelHeight * ASPECT_RATIO);
        } else {
            renderWidth = panelWidth;
            renderHeight = (int) (panelWidth / ASPECT_RATIO);
        }

        final int x = (panelWidth - renderWidth) / 2;
        final int y = (panelHeight - renderHeight) / 2;

        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setClip(x, y, renderWidth, renderHeight);
        g2d.translate(x, y);
        final double scale = Math.min(
                renderWidth / (double) REF_WIDTH,
                renderHeight / (double) REF_HEIGHT);
        g2d.scale(scale, scale);

        this.currentViewState.draw(g2d, REF_WIDTH, REF_HEIGHT, deltaTime); // TODO: fix here i think

        g.dispose();
    }

    /**
     * Returns the preferred size of the panel.
     *
     * @return the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(REF_WIDTH, REF_HEIGHT);
    }

    /**
     * Updates the panel state and changes the view state if necessary.
     *
     * @param deltaTime time elapsed since the last update
     */
    @Override
    public void update(final float deltaTime) {
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
     * Changes the current view state, calling onExit() and onEnter() as
     * appropriate.
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
