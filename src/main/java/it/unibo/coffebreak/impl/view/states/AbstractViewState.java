package it.unibo.coffebreak.impl.view.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.api.view.states.ViewState;
import it.unibo.coffebreak.impl.view.loader.ResourceLoader;

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
    public static final float TOP_HEIGHT = 0.05f;
    /**
     * Vertical position (as a fraction of total height) for the score value under
     * "HIGH SCORE".
     */
    public static final float SCORE_HEIGHT = 0.1f;

    /**
     * Vertical position (as a fraction of total height) for the VIEW title.
     */
    public static final float TITLE_HEIGHT = 0.33f;
    /**
     * Vertical position (as a fraction of total height) for the "Insert your name"
     * prompt.
     */
    public static final float MIDDLE_HEIGHT = 0.55f;
    /**
     * Vertical position (as a fraction of total height) for the "[ Save ]" button.
     */
    public static final float SAVE_HEIGHT = 0.75f;

    private final Loader resourceLoader = new ResourceLoader();
    private final Controller controller;

    /**
     * Constructs an AbstractViewState with the specified controller.
     *
     * @param controller the controller associated with this view state; must not be
     *                   null
     * @throws NullPointerException if {@code controller} is null
     */
    public AbstractViewState(final Controller controller) {
        this.controller = Objects.requireNonNull(controller, "The controller cannot be null");
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation does nothing.
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
     * Default implementation does nothing.
     * Subclasses may override to handle cleanup logic.
     * </p>
     */
    @Override
    public void onExit() {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     * Subclasses must implement their own drawing logic.
     */
    @Override
    public abstract void draw(Graphics2D g, int width, int height, float deltaTime);

    /**
     * Returns the resource loader used by this view state.
     * <p>
     * Intended for use only by subclasses to load fonts, images, or sounds.
     * </p>
     *
     * @return the {@link Loader} instance for this view
     */
    protected final Loader getResource() {
        return this.resourceLoader;
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
     * Method responsible for drawing the avaiable options in the current State.
     * 
     * @param g      the graphics context
     * @param width  the width of the window
     * @param height the height of the window
     */
    protected final void drawOptions(final Graphics2D g, final int height, final int width) {

        final var optionFont = getResource().loadFont(ResourceLoader.FONT_PATH).deriveFont(height * 0.055f);

        g.setFont(optionFont);

        final var options = this.controller.getGameState().getOptions();
        final int selected = options.indexOf(this.controller.getGameState().getSelectedOption());
        final int baseY = (int) (height * 0.5);
        final int stepY = (int) (height * 0.18);

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
