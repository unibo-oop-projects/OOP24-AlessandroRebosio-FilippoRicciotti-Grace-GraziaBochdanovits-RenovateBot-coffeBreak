package it.unibo.coffebreak.impl.view.render.entities.enemy;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * An abstract base class for rendering enemy entities in the game.
 * <p>
 * This class provides common rendering logic for all enemy types, specifically
 * handling
 * the case where an enemy has been destroyed. Concrete subclasses must
 * implement
 * the actual rendering of the enemy's visual representation.
 * </p>
 *
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEnemyRender extends AbstractEntityRender {

    /**
     * Constructs a new AbstractEnemyRender with the specified resource loader.
     *
     * @param resource the resource loader used to load images, sounds, and other
     *                 assets.
     */
    public AbstractEnemyRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof final Enemy enemy && !enemy.isDestroyed()) {
            this.renderEnemy(g, entity, deltaTime, width, height);
            super.draw(g, entity, deltaTime, width, height);
        }
    }

    /**
     * Renders the visual representation of the enemy entity.
     * <p>
     * Concrete subclasses must implement this method to provide the specific
     * rendering logic for their enemy type. This method is only called when
     * the enemy exists and hasn't been destroyed.
     * </p>
     *
     * @param g         the {@link Graphics2D} context to render onto
     * @param entity    the enemy entity to render
     * @param deltaTime the time in seconds since the last frame was rendered
     * @param width     the width available for rendering the entity
     * @param height    the height available for rendering the entity
     */
    protected abstract void renderEnemy(Graphics2D g, Entity entity, float deltaTime, int width, int height);
}
