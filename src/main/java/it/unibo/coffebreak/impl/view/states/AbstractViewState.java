package it.unibo.coffebreak.impl.view.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.sound.SoundManager;
import it.unibo.coffebreak.api.view.states.ViewState;
import it.unibo.coffebreak.impl.common.ResourceLoader;

/**
 * Abstract implementation of the {@link ViewState} interface.
 * <p>
 * Provides a base class for all view states with access to shared resources,
 * such as fonts, images, or audio files.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public abstract class AbstractViewState implements ViewState {

    /**
     * Vertical position (as a fraction of total height) for the "HIGH SCORE" label.
     */
    public static final float TOP_HEIGHT = 0.025f;
    /**
     * Vertical position (as a fraction of total height) for the score value under
     * "HIGH SCORE".
     */
    public static final float SCORE_HEIGHT = 0.045f;

    /**
     * Vertical position (as a fraction of total height) for the VIEW title.
     */
    public static final float TITLE_HEIGHT = 0.30f;
    /**
     * Vertical position (as a fraction of total height) for the "Insert your name"
     * prompt.
     */
    public static final float MIDDLE_HEIGHT = 0.55f;
    /**
     * Vertical position (as a fraction of total height) for the "[ Save ]" button.
     */
    public static final float SAVE_HEIGHT = 0.75f;

    private static final float DERIVE = 0.02f;

    private final Controller controller;
    private final Loader loader;
    private final SoundManager soundManager;

    /**
     * Constructs an AbstractViewState with the specified controller.
     *
     * @param controller   the controller associated with this view state
     * @param loader       the resource loader for graphics
     * @param soundManager the sound Manager responsible for playing the clips
     * @throws NullPointerException if {@code controller} is null
     */
    public AbstractViewState(final Controller controller, final Loader loader, final SoundManager soundManager) {
        this.controller = Objects.requireNonNull(controller, "The controller cannot be null");
        this.loader = Objects.requireNonNull(loader, "The loader cannot be null");
        this.soundManager = Objects.requireNonNull(soundManager, "The soundManager cannot be null");
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation.
     * Subclasses may override to handle setup logic.
     * </p>
     */
    @Override
    public void onEnter() {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation.
     * Subclasses may override to handle cleanup logic.
     * </p>
     */
    @Override
    public void onExit() {
        this.soundManager.stopAll();
    }

    /**
     * {@inheritDoc}
     * Subclasses must implement their own drawing logic.
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setFont(this.loader.loadFont(ResourceLoader.FONT_PATH).deriveFont(height * DERIVE));

        drawCenteredText(g, "HIGH SCORE", width, (int) (height * TOP_HEIGHT), Color.RED);
        drawCenteredText(g, "1UP", width / 3, (int) (height * TOP_HEIGHT), Color.RED);
        drawCenteredText(g, String.valueOf(controller.getHighestScore()), width, (int) (height * SCORE_HEIGHT),
                Color.WHITE);

        drawCenteredText(g, String.format("%06d", controller.getScoreValue()), width / 3,
                (int) (height * SCORE_HEIGHT),
                Color.WHITE);

        drawCenteredText(g, "L = " + String.format("%02d", controller.getLevelIndex()), width + width * 2 / 3,
                (int) (height * SCORE_HEIGHT),
                Color.BLUE);
    }

    /**
     * Returns the loader associated with this view state.
     *
     * @return the loader
     */
    protected final Loader getLoader() {
        return this.loader;
    }

    /**
     * Returns the controller associated with this view state.
     *
     * @return the controller instance
     */
    protected final Controller getController() {
        return this.controller;
    }

    /**
     * Returns the soundManager responsible for playing clips.
     *
     * @return soundManager
     */
    protected final SoundManager getSoundManager() {
        return this.soundManager;
    }

    /**
     * Method responsible for drawing the avaiable options in the current State.
     * 
     * @param g      the graphics context
     * @param width  the width of the window
     * @param height the height of the window
     */
    protected final void drawOptions(final Graphics2D g, final int height, final int width) {

        final var optionFont = loader.loadFont(ResourceLoader.FONT_PATH).deriveFont(height * DERIVE);

        g.setFont(optionFont);

        final var options = this.controller.getGameState().options();
        final int selected = options.indexOf(this.controller.getGameState().getSelectedOption());
        final int baseY = (int) (height * 0.40);
        final int stepY = (int) (height * 0.07);

        for (int i = 0; i < options.size(); i++) {
            final String text = options.get(i).toString();
            final int y = baseY + i * stepY;
            this.drawCenteredText(g, text, width, y, i == selected ? Color.YELLOW : Color.WHITE);
        }
    }

    /**
     * Method responsible for drawing the text Centered.
     * 
     * @param g     the graphics context
     * @param text  text that needs to be drawn
     * @param width possibly the width of the window
     * @param y     y-coordinate to draw the text to
     * @param color color to set the font to
     */
    protected final void drawCenteredText(final Graphics2D g, final String text, final int width, final int y,
            final Color color) {
        final var fm = g.getFontMetrics();
        final int x = (width - fm.stringWidth(text)) / 2;
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
