package it.unibo.coffebreak.model.impl.entity;

import java.util.Objects;

import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Represents Pauline, the character that needs to be rescued by Mario in the game.
 * 
 * <p>Pauline is a passive {@link GameEntity} that doesn't have active behaviors
 * but serves as the objective of the rescue mission.
 * 
 * <p>Characteristics:
 * <ul>
 *   <li>Static position (doesn't move on its own)</li>
 *   <li>Visual representation only</li>
 *   <li>Can be in rescued/rescuable state</li>
 * </ul>
 */
public final class Pauline extends GameEntity {

    private boolean rescued;

    /**
     * Creates a new Pauline instance at the specified position.
     * 
     * @param position the initial position (not null)
     * @param dimension the hitbox dimensions (not null)
     * @throws IllegalArgumentException if any parameter is null
     */
    public Pauline(final Position position, final Dimension dimension) {
        super(Objects.requireNonNull(position), Objects.requireNonNull(dimension));
        this.rescued = false;
    }

    /**
     * Marks Pauline as rescued.
     */
    public void rescue() {
        this.rescued = true;
    }

    /**
     * Checks if Pauline has been rescued.
     * 
     * @return true if rescued, false otherwise
     */
    public boolean isRescued() {
        return rescued;
    }

    /**
     * Empty implementation since Pauline doesn't require state updates.
     * 
     * @param deltaTime the time elapsed since last update (unused)
     */
    @Override
    public void update(final long deltaTime) {
    }
}
