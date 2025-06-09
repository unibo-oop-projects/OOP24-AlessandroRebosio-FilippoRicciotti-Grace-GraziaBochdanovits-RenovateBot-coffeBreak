package it.unibo.coffebreak.impl.view.panels;

import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.menu.MenuState;
import it.unibo.coffebreak.api.view.panels.GameStatePanel;
import it.unibo.coffebreak.api.view.renders.RenderManager;
import it.unibo.coffebreak.impl.view.renders.GameRenderManagerImpl;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;
import java.awt.Font;

/**
 * Panel for the Menu game state.
 */
public class MenuPanel implements GameStatePanel {

    private final MenuState menuState;

    private static Font TITLE_FONT = null;
    private static Font OPTION_FONT = null;
    private static final Color SELECTED_COLOR = Color.YELLOW;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    private static final int TITLE_Y = 150;
    private static final int START_GAME_Y = 300;
    private static final int EXIT_Y = 410;

    private static final int START_OPTION = 0;
    private static final int QUIT_OPTION = 1;

    private final int screenWidth, screenHeight;

    private final RenderManager renderManager;

    private final ResourceLoader resources;

    /**
     * Constructor for the MenuPanel.
     * 
     * @param menuState the MenuState to display
     */
    public MenuPanel(final ResourceLoader resources, final Model model, final int width,
            final int height) {
        this.renderManager = new GameRenderManagerImpl();
        this.screenHeight = height;
        this.screenWidth = width;
        this.resources = Objects.requireNonNull(resources);
        this.menuState = (MenuState) model.getGameState();
        TITLE_FONT = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(65.0f);
        OPTION_FONT = resources.loadFont("/fonts/ARCADECLASSIC.TTF").deriveFont(33.0f);

    }

    @Override
    public void render(final Graphics2D g, final int width, final int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setFont(TITLE_FONT);
        g.setColor(Color.BLUE);
        g.drawString("Coffee", (width - g.getFontMetrics().stringWidth("CoffeeBreak")) / 2, TITLE_Y);
        g.setColor(DEFAULT_COLOR);
        g.drawString("Break",
                ((width - g.getFontMetrics().stringWidth("CoffeeBreak")) / 2)
                        + g.getFontMetrics().stringWidth("Coffee"),
                TITLE_Y);

        g.setFont(OPTION_FONT);
        g.setColor(menuState.getSelectedOption() == START_OPTION ? SELECTED_COLOR : DEFAULT_COLOR);
        g.drawString("Start Game", (width - g.getFontMetrics().stringWidth("Start Game")) / 2, START_GAME_Y);
        g.setColor(menuState.getSelectedOption() == QUIT_OPTION ? SELECTED_COLOR : DEFAULT_COLOR);
        g.drawString("Exit", (width - g.getFontMetrics().stringWidth("Exit")) / 2, EXIT_Y);
    }

    @Override
    public void update() {

    }

}