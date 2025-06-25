package it.unibo.coffebreak.impl.view.states.menu;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.impl.common.ResourceLoader;
import it.unibo.coffebreak.impl.view.GameView;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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


    private final Font font;

    /**
     * Constructs the main menu view and loads required fonts.
     *
     * @param controller the controller to interact with the game logic
     * @param loader     the resource loader for graphics
     */
    public MenuView(final Controller controller, final Loader loader) {
        super(controller, loader);

        this.font = loader.loadFont(ResourceLoader.FONT_PATH);
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
        final String title = GameView.TITLE.replace(" ", "").toUpperCase(Locale.getDefault());
        int mid = (title.length() + 1) / 2;
        String left = title.substring(0, mid);
        String right = title.substring(mid);

        g.setFont(titleFont);

        int leftWidth = g.getFontMetrics().stringWidth(left);
        int rightWidth = g.getFontMetrics().stringWidth(right);
        int totalWidth = leftWidth + rightWidth;
        int x = (width - totalWidth) / 2;
        int y = (int) (height * TITLE_HEIGHT);

        g.setColor(Color.BLUE);
        g.drawString(left, x, y);

        g.setColor(Color.WHITE);
        g.drawString(right, x + leftWidth, y);

        super.drawOptions(g, height, width);
    }
}
