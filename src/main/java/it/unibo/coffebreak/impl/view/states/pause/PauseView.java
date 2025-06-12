package it.unibo.coffebreak.impl.view.states.pause;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.impl.view.loader.ResourceLoader;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

/**
 * View state responsible for rendering the Pause screen.
 * <p>
 * Displays the Pause title and the options such as "Resume" and "Exit"(Exit =>
 * Go to Main Menu).
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class PauseView extends AbstractViewState {

    private final Font font;

    /**
     * Constructs the pause view and loads required fonts.
     *
     * @param controller the controller to interact with the game logic
     */
    public PauseView(final Controller controller) {
        super(controller);

        this.font = super.getResource().loadFont(ResourceLoader.FONT_PATH);
    }

    /**
     * Draws the Pause background, title, and options.
     *
     * @param g         the graphics context
     * @param width     the width of the window
     * @param height    the height of the window
     * @param deltaTime
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        super.draw(g, width, height, deltaTime);

        final Font titleFont = this.font.deriveFont(height * 0.11f);

        g.setFont(titleFont);
        drawCenteredText(g, "PAUSED", width, (int) (height * TITLE_HEIGHT), Color.GREEN);

        super.drawOptions(g, height, width);
    }

}
