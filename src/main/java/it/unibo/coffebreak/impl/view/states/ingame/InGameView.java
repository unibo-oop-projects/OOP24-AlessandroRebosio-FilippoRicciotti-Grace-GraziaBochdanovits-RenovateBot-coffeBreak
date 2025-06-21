package it.unibo.coffebreak.impl.view.states.ingame;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.impl.view.render.GameRenderManager;
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

    private final RenderManager renderManager = new GameRenderManager(super.getResource());

    /**
     * Constructs an InGameView with the specified controller.
     * Initializes the render manager with a default resolution of 800x600 pixels.
     * 
     * @param controller the game controller that manages the game logic and
     *                   entities
     * @throws IllegalArgumentException if the controller is null
     */
    public InGameView(final Controller controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        super.draw(g, width, height, deltaTime);
        final List<Entity> entities = super.getController().getEntities();

        final int hudHeight = (int) (height * 0.10);
        final int renderAreaHeight = height - hudHeight;

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        for (final Entity e : entities) {
            minX = Math.min(minX, e.getPosition().x());
            minY = Math.min(minY, e.getPosition().y());
            maxX = Math.max(maxX, e.getPosition().x() + e.getDimension().width());
            maxY = Math.max(maxY, e.getPosition().y() + e.getDimension().height());
        }

        final double logicalWidth = maxX - minX;
        final double logicalHeight = maxY - minY;

        final double scaleX = width / logicalWidth;
        final double scaleY = renderAreaHeight / logicalHeight;
        final double scale = Math.min(scaleX, scaleY);

        final double scaledWidth = logicalWidth * scale;
        final double scaledHeight = logicalHeight * scale;

        final double offsetX = (width - scaledWidth) / 2.0;
        final double offsetY = (height - scaledHeight) / 2.0;

        final AffineTransform oldTransform = g.getTransform();
        g.translate(offsetX, offsetY);
        g.scale(scale, scale);

        renderManager.render(g, entities, width, renderAreaHeight, deltaTime);

        g.setTransform(oldTransform);
    }
}
