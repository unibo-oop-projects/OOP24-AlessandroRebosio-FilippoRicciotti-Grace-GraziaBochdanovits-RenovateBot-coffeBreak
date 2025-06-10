package it.unibo.coffebreak.impl.view.states.pause;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;
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

    private static final Color DEFAULT_COLOR = Color.WHITE;

    private final Font font;

    /**
     * Constructs the pause view and loads required fonts.
     *
     * @param controller the controller to interact with the game logic
     */
    public PauseView(Controller controller) {
        super(controller);

        this.font = super.getResource().loadFont(ResourceLoader.FONT_PATH);
    }

    /**
     * Draws the Pause background, title, and options.
     *
     * @param g      the graphics context
     * @param width  the width of the window
     * @param height the height of the window
     */
    @Override
    public void draw(Graphics2D g, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        final Font titleFont = this.font.deriveFont(height * 0.11f);
        final Font optionFont = this.font.deriveFont(height * 0.055f);

        g.setFont(titleFont);
        final var fmTitle = g.getFontMetrics();
        final int titleY = (int) (height * 0.22);
        final int titleX = (width - fmTitle.stringWidth("PAUSED")) / 2;
        g.setColor(Color.GREEN);
        g.drawString("PAUSED", titleX, titleY);

        g.setFont(optionFont);
        final var fmOption = g.getFontMetrics();

        if (super.getController().getGameState() instanceof final PauseModelState pauseState) {
            final var options = pauseState.getOptions();
            final int selected = options.indexOf(pauseState.getSelectedOption());
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

}
