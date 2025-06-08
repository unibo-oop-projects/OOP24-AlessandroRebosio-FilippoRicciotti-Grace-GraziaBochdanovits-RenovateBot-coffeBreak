package it.unibo.coffebreak.impl.view.renders.entityrenders;

import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.renders.EntityRender;
import it.unibo.coffebreak.impl.view.renders.AbstractScalableRender;

/**
 * An abstract implementation of {@link EntityRender} that provides basic functionality
 * for rendering entities with screen-relative scaling.
 * <p>
 * This class extends {@link AbstractScalableRender} to inherit scaling capabilities
 * based on screen dimensions. Concrete implementations should override the render
 * method to provide specific rendering logic for different entity types.
 * </p>
 * 
 * @see EntityRender
 * @see AbstractScalableRender
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEntityRender extends AbstractScalableRender implements EntityRender {

    /**
     * Constructs a new AbstractEntityRender with the specified screen dimensions.
     * The scaling will be calculated relative to these dimensions.
     *
     * @param screenWidth  the width of the screen used for scaling calculations
     * @param screenHeight the height of the screen used for scaling calculations
     */
    public AbstractEntityRender(final int screenWidth, final int screenHeight) {
        super(screenWidth, screenHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g,  final Entity entity) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        Objects.requireNonNull(entity, "Entity cannot be null");
        if (!canRender(entity)) {
            throw new IllegalArgumentException("Entity must be a Mario instance");
        }
    }

    /**
     * Gets the scaled width of the specified entity.
     * 
     * @param entity the entity to get the scaled width from
     * @return the scaled width of the entity
     */
    protected final int getScaledEntityWidth(final Entity entity) {
        return getScaledWidth((int) entity.getDimension().width());
    }

    /**
     * Gets the scaled height of the specified entity.
     * 
     * @param entity the entity to get the scaled height from
     * @return the scaled height of the entity
     */
    protected final int getScaledEntityHeight(final Entity entity) {
        return getScaledHeight((int) entity.getDimension().height());
    }
}
