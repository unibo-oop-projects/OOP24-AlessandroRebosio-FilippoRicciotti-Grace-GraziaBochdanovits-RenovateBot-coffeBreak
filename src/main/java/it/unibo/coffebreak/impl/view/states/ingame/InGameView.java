package it.unibo.coffebreak.impl.view.states.ingame;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Comparator;
import java.util.Optional;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.impl.view.render.GameRenderManager;
import it.unibo.coffebreak.impl.view.sound.GameSoundManager;
import it.unibo.coffebreak.api.view.sound.SoundManager.Event;
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

    /**
     * Constructs an InGameView with the specified controller.
     * Initializes the render manager with a default resolution of 800x600 pixels.
     * 
     * @param controller the game controller that manages the game logic and
     *                   entities
     * @param loader     the resource loader for graphics
     */
    public InGameView(final Controller controller, final Loader loader) {
        super(controller, loader);
        GameSoundManager.getInstance().loop(Event.BACKGROUND);
        this.renderManager = new GameRenderManager(loader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final int panelWidth, final int panelHeight,
            final float deltaTime) {
        final float marginRatio = 0.1f;
        super.draw(g, panelWidth, panelHeight, deltaTime);

        final int marginHoriz = (int) (panelWidth * marginRatio);
        final int marginVert = (int) (panelHeight * marginRatio);

        final int renderWidth = panelWidth - 2 * marginHoriz;
        final int renderHeight = panelHeight - 2 * marginVert;

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
    }
}
