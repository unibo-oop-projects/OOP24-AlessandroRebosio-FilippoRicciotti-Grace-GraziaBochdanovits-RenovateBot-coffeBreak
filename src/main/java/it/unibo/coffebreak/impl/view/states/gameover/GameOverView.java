package it.unibo.coffebreak.impl.view.states.gameover;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

public class GameOverView extends AbstractViewState {

    private static final Color DEFAULT_COLOR = Color.WHITE;

    private final Font font;

    public GameOverView(final Controller controller) {
        super(controller);
        this.font = super.getResource().loadFont(ResourceLoader.FONT_PATH);
    }

    @Override
    public void draw(Graphics2D g, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        final Font titleFont = this.font.deriveFont(height * 0.11f);
        final Font scoreFont = this.font.deriveFont(height * 0.04f);
        final var optionFont = this.font.deriveFont(height * 0.055f);

        final var controller = super.getController();
        final var gameState = controller.getGameState();

        g.setFont(scoreFont);
        drawCenteredText(g, "HIGH SCORE", width, (int) (height * 0.05), Color.RED);
        drawCenteredText(g, String.valueOf(controller.getHighestScore()), width, (int) (height * 0.1), DEFAULT_COLOR);

        g.setFont(titleFont);
        drawCenteredText(g, "GAME OVER", width, (int) (height * 0.33), Color.RED);

        g.setFont(scoreFont);
        drawCenteredText(g, "Insert your name:", width, (int) (height * 0.55), Color.LIGHT_GRAY);

        g.setFont(optionFont);
        final var fm = g.getFontMetrics();
        final var options = gameState.getOptions();
        final int selected = options.indexOf(gameState.getSelectedOption());

        final int baseY = (int) (height * 0.63);
        final int spacing = (int) (width * 0.05);
        final int totalWidth = 3 * fm.charWidth('W') + 2 * spacing;
        final int startX = (width - totalWidth) / 2;
        final String name = gameState.getName();

        for (int i = 0; i < 3; i++) {
            final String text = String.valueOf(name.charAt(i));
            final int letterX = startX + i * (fm.charWidth('W') + spacing);
            g.setColor(i == selected ? Color.YELLOW : Color.WHITE);
            g.drawString(text, letterX, baseY);
        }

        g.setFont(scoreFont);
        drawCenteredText(g, "[ SAVE ]", width, (int) (height * 0.75),
                (selected == options.size() - 1 ? Color.YELLOW : Color.WHITE));

    }

}
