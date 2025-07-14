package it.unibo.coffebreak.impl.view.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import it.unibo.coffebreak.api.common.Loader;
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

    private static final float TARGET_ASPECT_RATIO = 4f / 3f;
    private static final float SCREEN_SCALE = 0.7f;

    @Serial
    private static final long serialVersionUID = 1L;
    private transient Optional<ViewState> currentViewState = Optional.empty();
    private final transient Controller controller;
    private float deltaTime;

    private final transient ViewState menuView;
    private final transient ViewState inGameView;
    private final transient ViewState pauseView;
    private final transient ViewState gameOverView;

    /**
     * Constructs a GamePanel associated with the given controller and sets up the
     * KeyAdapter.
     *
     * @param controller   the controller to notify for input events
     * @param loader       the resource loader for graphics
     * @throws NullPointerException if either argument is null
     */
    public GamePanel(final Controller controller, final Loader loader) {
        super();

        this.controller = Objects.requireNonNull(controller, "The controller cannot be null");

        this.menuView = new MenuView(this.controller, loader);
        this.inGameView = new InGameView(this.controller, loader);
        this.pauseView = new PauseView(this.controller, loader);
        this.gameOverView = new GameOverView(this.controller, loader);

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
                repaint();
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

        this.currentViewState
                .ifPresent(viewState -> viewState.draw((Graphics2D) g, getWidth(), getHeight(), deltaTime));
    }

    /**
     * Returns the preferred size of the panel.
     *
     * @return the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        final Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int) (screenSize.width * SCREEN_SCALE);
        int height = (int) (width / TARGET_ASPECT_RATIO);

        if (height > screenSize.height * SCREEN_SCALE) {
            height = (int) (screenSize.height * SCREEN_SCALE);
            width = (int) (height * TARGET_ASPECT_RATIO);
        }

        return new Dimension(width, height);
    }

    /**
     * Updates the panel state and changes the view state if necessary.
     *
     * @param deltaTime time elapsed since the last update
     */
    @Override
    public void update(final float deltaTime) {
        this.deltaTime = deltaTime;
        final ViewState nextState = switch (this.controller.getGameState()) {
            case final MenuModelState menu -> menuView;
            case final InGameModelState inGame -> inGameView;
            case final PauseModelState pause -> pauseView;
            case final GameOverModelState gameOver -> gameOverView;
            default -> null;
        };

        if (nextState != null && !this.currentViewState.map(nextState::equals).orElse(false)) {
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
        this.currentViewState.ifPresent(ViewState::onExit);
        this.currentViewState = Optional.of(Objects.requireNonNull(newView, "The newView cannot be null"));
        this.currentViewState.ifPresent(ViewState::onEnter);
    }
}
