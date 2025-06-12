package it.unibo.coffebreak.impl.view.states.menu;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.impl.view.loader.ResourceLoader;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * View state responsible for rendering the main menu screen.
 * <p>
 * Displays the game title and menu options such as "Start Game" and "Exit".
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class MenuView extends AbstractViewState {

    private static final Color DEFAULT_COLOR = Color.WHITE;

    private final Font font;

    /**
     * Constructs the main menu view and loads required fonts.
     *
     * @param controller the controller to interact with the game logic
     */
    public MenuView(final Controller controller) {
        super(controller);

        this.font = super.getResource().loadFont(ResourceLoader.FONT_PATH);
    }

    /**
     * Draws the main menu background, title, and options.
     *
     * @param g         the graphics context
     * @param width     the width of the window
     * @param height    the height of the window
     * @param deltaTime
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        super.draw(g, width, height, deltaTime);

        final Font titleFont = this.font.deriveFont(height * 0.05f);

        g.setFont(titleFont);
        final int coffeeX = width - g.getFontMetrics().stringWidth("BREAK");
        final int breakX = width + g.getFontMetrics().stringWidth("COFFEE");
        drawCenteredText(g, "COFFEE", coffeeX, (int) (height * TITLE_HEIGHT), Color.BLUE);
        drawCenteredText(g, "BREAK", breakX, (int) (height * TITLE_HEIGHT), DEFAULT_COLOR);

        super.drawOptions(g, height, width);
    }
}
