package it.unibo.coffebreak.impl.view.renders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.view.renders.EntityRender;

/**
 * A renderer for ladder entities that draws them as red rectangles on the screen.
 * This class extends {@link AbstractScalableRender} to provide scaling functionality
 * based on the screen dimensions.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class LadderRender extends AbstractScalableRender implements EntityRender {

    private static final int LADDER_WIDTH = 20;
    private static final int LADDER_HEIGHT = 80;

    /**
     * Constructs a new LadderRender with the specified screen dimensions.
     * The ladder dimensions will be scaled according to these dimensions.
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    public LadderRender(final int screenWidth, final int screenHeight) {
        super(LADDER_WIDTH, LADDER_HEIGHT, screenWidth, screenHeight);
    }

    /**
     * Renders the ladder entity as a red rectangle on the graphics context.
     * The size of the rectangle is determined by the scaled dimensions.
     *
     * @param g      the graphics context to render on; cannot be null
     * @param entity the ladder entity to render
     * @throws NullPointerException if the graphics context is null
     * @throws IllegalArgumentException if the entity is not a Barrel instance
     */
    @Override
    public void render(final Graphics2D g, final Entity entity) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        if (!canRender(entity)) {
            throw new IllegalArgumentException("Entity must be a Mario instance");
        }
        g.setColor(Color.RED);
        g.drawRect((int) entity.getPosition().x(), (int) entity.getPosition().y(), getScaledWidth(), getScaledHeight());
    }

    /**
     * Checks if this renderer can render the specified entity.
     *
     * @param entity the entity to check
     * @return true if the entity is an instance of {@link Ladder}, false otherwise
     */
    @Override
    public boolean canRender(final Entity entity) {
        return entity instanceof Ladder;
    }
}
