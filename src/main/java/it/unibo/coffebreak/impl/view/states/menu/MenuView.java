package it.unibo.coffebreak.impl.view.states.menu;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;
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
     * @param g      the graphics context
     * @param width  the width of the window
     * @param height the height of the window
     * @param deltaTime
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        final Font titleFont = this.font.deriveFont(height * 0.11f);
        final Font optionFont = this.font.deriveFont(height * 0.055f);
        final Font scoreFont = this.font.deriveFont(height * 0.04f);

        g.setFont(scoreFont);

        final var fmScore = g.getFontMetrics();
        final int scoreY1 = (int) (height * 0.05);
        final int scoreX1 = (width - fmScore.stringWidth("HIGH SCORE")) / 2;
        final int scoreY2 = (int) (height * 0.1);
        final String highestScore = Integer.toString(super.getController().getHighestScore());
        final int scoreX2 = (width - fmScore.stringWidth(highestScore)) / 2;

        g.setColor(Color.RED);
        g.drawString("HIGH SCORE", scoreX1, scoreY1);
        g.setColor(DEFAULT_COLOR);
        g.drawString(highestScore, scoreX2, scoreY2);

        g.setFont(titleFont);
        final var fmTitle = g.getFontMetrics();
        final int titleY = (int) (height * 0.33);
        final int titleX = (width - fmTitle.stringWidth("COFFEEBREAK")) / 2;
        g.setColor(Color.BLUE);
        g.drawString("COFFEE", titleX, titleY);
        g.setColor(DEFAULT_COLOR);
        g.drawString("BREAK", titleX + fmTitle.stringWidth("COFFEE"), titleY);

        g.setFont(optionFont);
        final var fmOption = g.getFontMetrics();

        final var options = super.getController().getGameState().getOptions();
        final int selected = options.indexOf(super.getController().getGameState().getSelectedOption());
        final int baseY = (int) (height * 0.5);
        final int stepY = (int) (height * 0.18);

        for (int i = 0; i < options.size(); i++) {
            final String text = options.get(i).toString();
            final int y = baseY + i * stepY;
            g.setColor(i == selected ? Color.YELLOW : DEFAULT_COLOR);
            g.drawString(text, (width - fmOption.stringWidth(text)) / 2, y);
        }
    }
}
