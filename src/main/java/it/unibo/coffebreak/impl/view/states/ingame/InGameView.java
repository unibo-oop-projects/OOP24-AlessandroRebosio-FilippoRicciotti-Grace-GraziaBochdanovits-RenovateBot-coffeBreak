package it.unibo.coffebreak.impl.view.states.ingame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Comparator;
import java.util.Optional;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.api.view.sound.SoundManager;
import it.unibo.coffebreak.api.view.sound.SoundManager.Event;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.view.render.GameRenderManager;
import it.unibo.coffebreak.impl.view.render.entities.mario.MarioRender;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

/**
 * Represents the in-game view state of the game.
 * This class handles the rendering of game entities during gameplay
 * using a dedicated RenderManager.
 * 
 * <p>
 * It extends {@link AbstractViewState} to inherit common view state
 * functionality
 * and implements the game-specific rendering logic.
 * </p>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class InGameView extends AbstractViewState {

    private final RenderManager renderManager;
    private boolean isJumping;

    /**
     * Constructs an InGameView with the specified controller.
     * Initializes the render manager with a default resolution of 800x600 pixels.
     * 
     * @param controller   the game controller that manages the game logic and
     *                     entities
     * @param loader       the resource loader for graphics
     * 
     * @param soundManager the sound Manager responsible for playing the clips
     */
    public InGameView(final Controller controller, final Loader loader, final SoundManager soundManager) {
        super(controller, loader, soundManager);
        this.renderManager = new GameRenderManager(loader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnter() {
        getSoundManager().loop(Event.BACKGROUND);
        this.isJumping = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final int panelWidth, final int panelHeight, final float deltaTime) {
        final float marginRatio = 0.1f;
        super.draw(g, panelWidth, panelHeight, deltaTime);

        final int marginHoriz = (int) (panelWidth * marginRatio);
        final int marginVert = (int) (panelHeight * marginRatio);

        final int renderWidth = panelWidth - 2 * marginHoriz;
        final int renderHeight = panelHeight - 2 * marginVert;

        final var player = getController().getMainCharacter().get(); // TODO: is ClimbingSound

        final boolean marioJumped = player.isJumping();

        if (marioJumped && !isJumping) {
                isJumping = true;
                getSoundManager().play(Event.JUMP);
        } else if (!marioJumped) {
                isJumping = false;
        }

        final Optional<Entity> bottomRightPlatform = getController().getEntities().stream()
                        .filter(Platform.class::isInstance)
                        .max(Comparator.comparingDouble(e -> e.getPosition().x() + e.getPosition().y()));

        final double platformRight = bottomRightPlatform.get().getPosition().x()
                        + bottomRightPlatform.get().getDimension().width();
        final double platformBottom = bottomRightPlatform.get().getPosition().y()
                        + bottomRightPlatform.get().getDimension().height();

        final double scaleX = renderWidth / platformRight;
        final double scaleY = renderHeight / platformBottom;
        final double scale = Math.min(scaleX, scaleY);

        final double scaledWidth = platformRight * scale;
        final double scaledHeight = platformBottom * scale;

        final double offsetX = marginHoriz + (renderWidth - scaledWidth) / 2;
        final double offsetY = marginVert + (renderHeight - scaledHeight) / 2;

        final AffineTransform oldTransform = g.getTransform();
        g.translate(offsetX, offsetY);
        g.scale(scale, scale);

        this.renderManager.render(g, getController().getEntities(), (int) platformRight, (int) platformBottom,
                        deltaTime);

        g.setTransform(oldTransform);

        final int lives = getController().getCharacterLives();

        final int marioIconSize = (int) (panelHeight * 0.03f);
        final BoundigBox scaledDimension = new BoundigBox(marioIconSize, marioIconSize);

        final int oneUpX = panelWidth / 6;

        final int marioY = (int) (panelHeight * SCORE_HEIGHT) + (int) (panelHeight * 0.04f);

        final int spacing = (int) (marioIconSize * 0.2f);
        final int totalWidth = lives * marioIconSize + (lives - 1) * spacing;

        final int startX = oneUpX - totalWidth / 2;

        for (int i = 0; i < lives; i++) {
                final int x = startX + i * (marioIconSize + spacing);
                final Position pos = new Position(x, marioY);
                new MarioRender(getLoader()).draw(g, new Mario(pos, scaledDimension), deltaTime, panelWidth,
                                panelHeight);
        }

        final int bonusLabelY = (int) (panelHeight * SCORE_HEIGHT) + (int) (panelHeight * 0.04f);
        final int bonusValueY = bonusLabelY + (int) (panelHeight * 0.035f);
        final int bonusX = panelWidth + panelWidth * 2 / 3;

        drawCenteredText(g, "BONUS", bonusX, bonusLabelY, Color.MAGENTA);
        drawCenteredText(g, String.valueOf(getController().getBonusValue()), bonusX, bonusValueY, Color.WHITE);
    }
}
