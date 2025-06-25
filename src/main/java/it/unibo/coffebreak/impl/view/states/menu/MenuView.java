package it.unibo.coffebreak.impl.view.states.menu;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.impl.common.ResourceLoader;
import it.unibo.coffebreak.impl.view.GameView;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Locale;

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
    private final List<Entry> leaderBoard;

    /**
     * Constructs the main menu view and loads required fonts.
     *
     * @param controller the controller to interact with the game logic
     */
    public MenuView(final Controller controller) {
        super(controller);

        this.font = super.getResource().loadFont(ResourceLoader.FONT_PATH);
        leaderBoard = controller.getLeaderBoard();
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
        final Font boardFont = this.font.deriveFont(height * 0.03f);
        final String title = GameView.TITLE.toUpperCase(Locale.getDefault());
        final String[] titleParts = title.split(" ", 2);

        g.setFont(titleFont);
        final int coffeeX = width - g.getFontMetrics().stringWidth(titleParts[0] + titleParts[1]) / 2;
        final int breakX = width + g.getFontMetrics().stringWidth(titleParts[0] + titleParts[1]) / 2;
        drawCenteredText(g, titleParts[0], coffeeX, (int) (height * TITLE_HEIGHT), Color.BLUE);
        drawCenteredText(g, titleParts[1], breakX, (int) (height * TITLE_HEIGHT), DEFAULT_COLOR);

        super.drawOptions(g, height, width);

        g.setFont(boardFont);
        final int boardY = (int) (height * 0.60);
        drawCenteredText(g, "RANK  SCORE  NAME ", width, boardY, Color.CYAN);

        for (int i = 0; i < leaderBoard.size(); i++) {
            final Entry entry = leaderBoard.get(i);
            final String scoreFormatted = String.format("%06d", entry.score());
            final String text = i + 1 + ".   " + scoreFormatted + "  " + entry.name() + "  ";
            final int baseY = (int) (height * 0.65);
            final int stepY = (int) (height * 0.03);
            final int y = baseY + i * stepY;

            drawCenteredText(g, text, width, y, i < 3 ? Color.RED : Color.PINK);
        }

    }
}
