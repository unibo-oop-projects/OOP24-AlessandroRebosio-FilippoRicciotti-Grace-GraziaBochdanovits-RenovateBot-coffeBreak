package it.unibo.coffebreak.impl.view.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.api.view.panel.GameStatePanel;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

/**
 * Panel for the Pause game state.
 */
public class PausePanel implements GameStatePanel {

    private static final float TITLE_SIZE = 65.0f;

    private static final int PAUSE_SIZE = 60;
    private static final float OPTION_SIZE = 33.0f;

    private static final Font PAUSE_FONT = new Font("Arial", Font.BOLD, PAUSE_SIZE);
    private static final Color SELECTED_COLOR = Color.YELLOW;

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final int TITLE_Y = 150;

    private static final int START_GAME_Y = 300;
    private static final int MENU_Y = 400;

    private static final int EXIT_Y = 500;
    private static final int START_OPTION = 0;
    private static final int MENU_OPTION = 1;

    private static final int QUIT_OPTION = 2;

    private final Font titleFont;
    private final Font optionFont;
    private final ModelState pauseState;

    /**
     * Constructor for the Pause Panel.
     * 
     * @param resources  ResourceLoader needed for the fonts
     * @param controller controller for retrieving the gamestate
     * 
     */
    public PausePanel(final ResourceLoader resources, final Controller controller) {

        this.pauseState = Objects.requireNonNull(controller, "controller must not be null").getGameState();
        titleFont = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(TITLE_SIZE);
        optionFont = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(OPTION_SIZE);

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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setFont(titleFont);
        g.setColor(Color.RED);
        g.drawString("PAUSED", (width - g.getFontMetrics().stringWidth("PAUSED II")) / 2, TITLE_Y);
        g.setFont(PAUSE_FONT);
        g.setColor(DEFAULT_COLOR);
        g.drawString("II",
                ((width - g.getFontMetrics().stringWidth("PAUSED II")) / 2)
                        + g.getFontMetrics().stringWidth("PAUSED "),
                TITLE_Y);

        g.setFont(optionFont);
        g.setColor(pauseState.getSelectedOption() == START_OPTION ? SELECTED_COLOR : DEFAULT_COLOR);
        g.drawString("Start Game", (width - g.getFontMetrics().stringWidth("Start Game")) / 2, START_GAME_Y);
        g.setColor(pauseState.getSelectedOption() == MENU_OPTION ? SELECTED_COLOR : DEFAULT_COLOR);
        g.drawString("Menu", (width - g.getFontMetrics().stringWidth("Menu")) / 2, MENU_Y);
        g.setColor(pauseState.getSelectedOption() == QUIT_OPTION ? SELECTED_COLOR : DEFAULT_COLOR);
        g.drawString("Exit", (width - g.getFontMetrics().stringWidth("Exit")) / 2, EXIT_Y);

    }

}
