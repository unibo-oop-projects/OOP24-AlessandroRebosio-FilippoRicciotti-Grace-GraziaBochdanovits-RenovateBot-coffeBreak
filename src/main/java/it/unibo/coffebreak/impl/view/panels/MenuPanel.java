package it.unibo.coffebreak.impl.view.panels;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.api.view.panels.GameStatePanel;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;
import java.awt.Font;

/**
 * Panel for the Menu game state.
 */
public class MenuPanel implements GameStatePanel {

    private static final float TITLE_SIZE = 65.0f;

    private static final float OPTION_SIZE = 33.0f;
    private static final Color SELECTED_COLOR = Color.YELLOW;

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final int TITLE_Y = 150;
    private static final int START_GAME_Y = 300;
    private static final int EXIT_Y = 410;

    private static final int START_OPTION = 0;
    private static final int QUIT_OPTION = 1;
    private final ModelState menuState;

    private final Font titleFont;
    private final Font optionFont;

    /**
     * Constructor for the Menu Panel.
     * 
     * @param resources  ResourceLoader needed for the fonts
     * @param controller controller for retrieving the gamestate
     */
    public MenuPanel(final ResourceLoader resources, final Controller controller) {

        this.menuState = Objects.requireNonNull(controller, "controller must not be null").getGameState();
        titleFont = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(TITLE_SIZE);
        optionFont = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(OPTION_SIZE);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final int width, final int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setFont(titleFont);
        g.setColor(Color.BLUE);
        g.drawString("Coffee", (width - g.getFontMetrics().stringWidth("CoffeeBreak")) / 2, TITLE_Y);
        g.setColor(DEFAULT_COLOR);
        g.drawString("Break",
                ((width - g.getFontMetrics().stringWidth("CoffeeBreak")) / 2)
                        + g.getFontMetrics().stringWidth("Coffee"),
                TITLE_Y);

        g.setFont(optionFont);
        g.setColor(menuState.getSelectedOption() == START_OPTION ? SELECTED_COLOR : DEFAULT_COLOR);
        g.drawString("Start Game", (width - g.getFontMetrics().stringWidth("Start Game")) / 2, START_GAME_Y);
        g.setColor(menuState.getSelectedOption() == QUIT_OPTION ? SELECTED_COLOR : DEFAULT_COLOR);
        g.drawString("Exit", (width - g.getFontMetrics().stringWidth("Exit")) / 2, EXIT_Y);
    }

    @Override
    public void update() {

    }

}
