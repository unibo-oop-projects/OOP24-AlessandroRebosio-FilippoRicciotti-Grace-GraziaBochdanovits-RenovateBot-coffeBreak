package it.unibo.coffebreak.model.impl.entity;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entity.Entity;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Base abstract class for all game entities that have a physical representation.
 * <p>
 * Provides fundamental properties and behavior for entities that exist in the game world,
 * including their position and dimensions. This class serves as the foundation for
 * all concrete entity implementations in the game.
 * </p>
 * 
 * @see Entity
 */
public abstract class GameEntity implements Entity {

    /**
     * The current position of the entity in game world coordinates.
     * Expressed as a {@link Position} object containing x and y coordinates.
     */
    private Position position;

    /**
     * The physical dimensions of the entity.
     * Expressed as a {@link Dimension} object containing width and height.
     * This field is marked {@code final} and cannot be modified after construction.
     */
    private final Dimension dimension;

    /**
     * Constructs a new game entity with the specified position and dimensions.
     *
     * @param position the initial position of the entity (cannot be {@code null})
     * @param dimension the dimensions of the entity (cannot be {@code null})
     * @throws NullPointerException if either position or dimension is {@code null}
     */
    public GameEntity(final Position position, final Dimension dimension) {
        this.position = Objects.requireNonNull(position, "Position cannot be null");
        this.dimension = Objects.requireNonNull(dimension, "Dimension cannot be null");
    }

    /**
     * Updates the entity's state based on elapsed game time.
     * 
     * <p>Base implementation is empty, suitable for static entities that don't move or change state.
     * Moving entities should override this method to implement their specific behavior.</p>
     *
     * @param deltaTime the time elapsed since last update in milliseconds (must be positive)
     * @throws IllegalArgumentException if deltaTime is negative
    */
    public void update(final float deltaTime) {
        // Base implementation does nothing (for static entities)
    }
    /**
     * Checks if this entity's bounding box intersects with another entity's bounding box.
     * 
     * <p>Collision occurs when both entities overlap on both X and Y axes,
     * including edge-to-edge contact.</p>
     *
     * @param other the entity to check collision with (not null)
     * @return true if entities collide, false otherwise
     * @throws NullPointerException if other is null
     */
    public boolean collidesWith(final GameEntity other) {
        return this.getPosition().x() < other.getPosition().x() + other.getDimension().width()
                && this.getPosition().x() + this.getDimension().width() > other.getPosition().x()
                && this.getPosition().y() < other.getPosition().y() + other.getDimension().height()
                && this.getPosition().y() + this.getDimension().height() > other.getPosition().y();
    }

    /**
     * Updates the entity's position in the game world.
     *
     * @param position the new position to set (cannot be {@code null})
     * @throws NullPointerException if position is {@code null}
     */
    public void setPosition(final Position position) {
        this.position = Objects.requireNonNull(position, "Position cannot be null");
    }

    /**
     * Gets the current position of the entity.
     *
     * @return the current position of the entity (never {@code null})
     */
    @Override
    public final Position getPosition() {
        return this.position; 
    }

    /**
     * Gets the dimensions of the entity.
     *
     * @return the dimensions of the entity (never {@code null})
     */
    @Override
    public final Dimension getDimension() {
        return this.dimension;
    }
}
