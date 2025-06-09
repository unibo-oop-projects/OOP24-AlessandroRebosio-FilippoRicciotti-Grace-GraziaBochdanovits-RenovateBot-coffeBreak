package it.unibo.coffebreak.impl.view.states.menu;

import it.unibo.coffebreak.api.controller.Controller;
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

    private static final float TITLE_SIZE = 65.0f;
    private static final float OPTION_SIZE = 33.0f;

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final int TITLE_Y = 150;
    private static final int START_GAME_Y = 300;
    private static final int EXIT_Y = 410;

    private final Font titleFont;
    private final Font optionFont;

    /**
     * Constructs the main menu view and loads required fonts.
     *
     * @param controller the controller to interact with the game logic
     */
    public MenuView(final Controller controller) {
        super(controller);

        titleFont = super.getResource().loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(TITLE_SIZE);
        optionFont = super.getResource().loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(OPTION_SIZE);
    }

    /**
     * Draws the main menu background, title, and options.
     *
     * @param g      the graphics context
     * @param width  the width of the window
     * @param height the height of the window
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height) {
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        // Title
        g.setFont(titleFont);
        g.setColor(Color.BLUE);
        g.drawString("Coffee", (width - g.getFontMetrics().stringWidth("CoffeeBreak")) / 2, TITLE_Y);
        g.setColor(DEFAULT_COLOR);
        g.drawString("Break",
                ((width - g.getFontMetrics().stringWidth("CoffeeBreak")) / 2)
                        + g.getFontMetrics().stringWidth("Coffee"),
                TITLE_Y);

        // Options
        g.setFont(optionFont);
        g.drawString("Start Game", (width - g.getFontMetrics().stringWidth("Start Game")) / 2, START_GAME_Y);
        g.drawString("Exit", (width - g.getFontMetrics().stringWidth("Exit")) / 2, EXIT_Y);
    }
}
