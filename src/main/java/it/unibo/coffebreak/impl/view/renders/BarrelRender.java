package it.unibo.coffebreak.impl.view.renders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.view.renders.EntityRender;

/**
 * A renderer for Barrel entities that draws them as blue circle on the screen.
 * This class extends {@link AbstractScalableRender} to provide scaling functionality
 * based on the screen dimensions.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class BarrelRender extends AbstractScalableRender implements EntityRender {

    private static final int BARREL_WIDTH = 30;
    private static final int BARREL_HEIGHT = 30;

    /**
     * Constructs a new BarrelRender with the specified screen dimensions.
     * The barrel dimensions will be scaled according to these dimensions.
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    public BarrelRender(final int screenWidth, final int screenHeight) {
        super(BARREL_WIDTH, BARREL_HEIGHT, screenWidth, screenHeight);
    }

    /**
     * Renders the barrel entity as a blue rectangle on the graphics context.
     * The size of the rectangle is determined by the scaled dimensions.
     *
     * @param g      the graphics context to render on; cannot be null
     * @param entity the barrel entity to render; must be an instance of {@link Barrel}
     * @throws NullPointerException if the graphics context is null
     * @throws IllegalArgumentException if the entity is not a Barrel instance
     */
    @Override
    public void render(final Graphics2D g, final Entity entity) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        if (!canRender(entity)) {
            throw new IllegalArgumentException("Entity must be a Mario instance");
        }
        g.setColor(Color.BLUE);
        g.fillOval((int) entity.getPosition().x(), (int) entity.getPosition().y(), getScaledWidth(), getScaledHeight());
    }

    /**
     * Checks if this renderer can render the specified entity.
     *
     * @param entity the entity to check
     * @return true if the entity is an instance of {@link Barrel}, false otherwise
     */
    @Override
    public boolean canRender(final Entity entity) {
        return entity instanceof Barrel;
    }
}
