package it.unibo.coffebreak.impl.view.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;
import java.awt.Font;

import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.score.entry.Entry;
import it.unibo.coffebreak.api.view.panels.GameStatePanel;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

/**
 * Panel for the Game Over state.
 */
public class GameOverPanel implements GameStatePanel {

    private static final Color BG_COLOR = Color.BLACK;
    private static final Color BOARD_COLOR = Color.ORANGE;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    private static final Color TITE_COLOR = Color.RED;
    private static final int TITLE_Y = 150;
    private static final int BOARD_Y = 250;
    private static final int MENU_Y = 410;

    private static final float TITLE_SIZE = 65.0f;
    private static final float BOARD_SIZE = 22.0f;
    private static final float OPTION_SIZE = 33.0f;

    private final Font titleFont;
    private final Font optionFont;
    private final Font boardFont;

    private final Model model;

    /**
     * Constructor for the Game Over Panel.
     * 
     * @param resources ResourceLoader needed for the fonts
     * @param model     model for retrieving the gamestate and the Leaderboard
     */
    public GameOverPanel(final ResourceLoader resources, final Model model) {
        this.model = Objects.requireNonNull(model, "Model must not be null");
        titleFont = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(TITLE_SIZE);
        optionFont = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(OPTION_SIZE);
        boardFont = resources.loadFont("/fonts/PressStart2P-Regular.ttf").deriveFont(BOARD_SIZE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final int width, final int height) {

        g.setColor(BG_COLOR);
        g.fillRect(0, 0, width, height);
        g.setColor(TITE_COLOR);
        g.setFont(titleFont);
        g.drawString("GAME OVER", (width - g.getFontMetrics().stringWidth("GAME OVER")) / 2, TITLE_Y);

        g.setFont(boardFont);
        g.setColor(BOARD_COLOR);
        final List<Entry> leaderboard = model.getLeaderBoard();
        for (int i = 0; i < leaderboard.size(); i++) {
            final Entry entry = leaderboard.get(i);
            final String line = String.format("%d. %s - %d", i + 1, entry.getName(), entry.getScore());
            g.drawString(line, (width - g.getFontMetrics().stringWidth(line)) / 2, BOARD_Y + i);
        }
        // TODO: enter player name for leaderboard

        g.setFont(optionFont);
        g.setColor(DEFAULT_COLOR);
        g.drawString("Menu", (width - g.getFontMetrics().stringWidth("MENU")) / 2, MENU_Y);

    }

}
