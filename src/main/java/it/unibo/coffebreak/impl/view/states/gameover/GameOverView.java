package it.unibo.coffebreak.impl.view.states.gameover;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.impl.common.ResourceLoader;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

/**
 * View state responsible for rendering the GAme Over screen.
 * <p>
 * Displays the game over title, your high Score and an interface to insert your
 * nickname.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class GameOverView extends AbstractViewState {

    private static final Color DEFAULT_COLOR = Color.WHITE;

    private final Font font;

    /**
     * Constructs the Game Over view and loads required fonts.
     *
     * @param controller the controller to interact with the game logic
     * @param loader     the resource loader for graphics
     */
    public GameOverView(final Controller controller, final Loader loader) {
        super(controller, loader);
        this.font = loader.loadFont(ResourceLoader.FONT_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        super.draw(g, width, height, deltaTime);

        final Font titleFont = this.font.deriveFont(height * 0.05f);
        final Font labelFont = this.font.deriveFont(height * 0.02f);
        final var nameFont = this.font.deriveFont(height * 0.02f);

        final var controller = super.getController();
        final var gameState = controller.getGameState();

        g.setFont(titleFont);
        drawCenteredText(g, "GAME OVER", width, (int) (height * TITLE_HEIGHT), Color.RED);

        g.setFont(labelFont);
        drawCenteredText(g, "Insert your name:", width, (int) (height * MIDDLE_HEIGHT), Color.LIGHT_GRAY);

        g.setFont(nameFont);
        // final var fm = g.getFontMetrics();
        final var options = gameState.getOptions();
        final int selected = options.indexOf(gameState.getSelectedOption());

        // final int baseY = (int) (height * 0.63);
        // final int spacing = (int) (width * 0.05);
        // final int totalWidth = 3 * fm.charWidth('W') + 2 * spacing;
        // final int startX = (width - totalWidth) / 2;
        // final String name = gameState.getName();

        for (int i = 0; i < 3; i++) {
            // final String text = String.valueOf(name.charAt(i));
            // final int letterX = startX + i * (fm.charWidth('W') + spacing);
            g.setColor(i == selected ? Color.YELLOW : DEFAULT_COLOR);
            // g.drawString(text, letterX, baseY);
        }

        g.setFont(labelFont);
        drawCenteredText(g, "[ SAVE ]", width, (int) (height * SAVE_HEIGHT),
                selected == options.size() - 1 ? Color.YELLOW : DEFAULT_COLOR);

    }

}
