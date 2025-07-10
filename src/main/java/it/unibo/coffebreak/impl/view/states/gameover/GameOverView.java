package it.unibo.coffebreak.impl.view.states.gameover;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.sound.SoundManager;
import it.unibo.coffebreak.api.view.sound.SoundManager.Event;
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

    private final Font font;

    /**
     * Constructs the Game Over view and loads required fonts.
     *
     * @param controller   the controller to interact with the game logic
     * @param loader       the resource loader for graphics
     * @param soundManager the sound Manager responsible for playing the clips
     */
    public GameOverView(final Controller controller, final Loader loader, final SoundManager soundManager) {
        super(controller, loader, soundManager);
        this.font = loader.loadFont(ResourceLoader.FONT_PATH);
        soundManager.stopAll();
        soundManager.play(Event.DEATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        super.draw(g, width, height, deltaTime);

        final Font titleFont = this.font.deriveFont(height * 0.05f);
        final Font subTitles = this.font.deriveFont(height * 0.02f);

        g.setFont(titleFont);
        drawCenteredText(g, "GAME OVER", width, (int) (height * TITLE_HEIGHT), Color.RED);

        g.setFont(subTitles);
        drawCenteredText(g, "Press enter to menu...", width, (int) (height * MIDDLE_HEIGHT), Color.GRAY);
    }

}
