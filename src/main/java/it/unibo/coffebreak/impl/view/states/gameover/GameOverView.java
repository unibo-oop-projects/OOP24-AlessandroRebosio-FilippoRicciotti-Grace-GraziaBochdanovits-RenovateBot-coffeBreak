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
        final int titleX = (width - fmTitle.stringWidth("GAME OVER")) / 2;
        g.setColor(Color.RED);
        g.drawString("GAME OVER", titleX, titleY);

        final var optionFont = getResource().loadFont(ResourceLoader.FONT_PATH).deriveFont(height * 0.055f);

        g.setFont(scoreFont);
        final String insertText = "Insert your name:";
        final var fmInsert = g.getFontMetrics();
        final int insertY = (int) (height * 0.55);
        final int insertX = (width - fmInsert.stringWidth(insertText)) / 2;
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(insertText, insertX, insertY);

        g.setFont(optionFont);

        final var options = super.getController().getGameState().getOptions();
        final int selected = options.indexOf(super.getController().getGameState().getSelectedOption());
        final int baseY = (int) (height * 0.63);
        final int spacing = (int) (width * 0.05);
        final int totalWidth = 3 * fmInsert.charWidth('W') + 2 * spacing;
        final int startX = (width - totalWidth) / 2;
        final String name = super.getController().getGameState().getName();

        for (int i = 0; i < 3; i++) {
            final String text = String.valueOf(name.charAt(i));
            final int letterX = startX + i * (fmInsert.charWidth('W') + spacing);
            g.setColor(i == selected ? Color.YELLOW : Color.WHITE);
            g.drawString(text, letterX, baseY);
        }
        g.setFont(scoreFont);
        final String saveText = "[ Save ]";
        final int saveY = (int) (height * 0.75);
        final int saveX = (width - fmInsert.stringWidth(saveText)) / 2;
        g.setColor(selected == options.size() - 1 ? Color.YELLOW : Color.WHITE);
        g.drawString(saveText, saveX, saveY);

    }

}
