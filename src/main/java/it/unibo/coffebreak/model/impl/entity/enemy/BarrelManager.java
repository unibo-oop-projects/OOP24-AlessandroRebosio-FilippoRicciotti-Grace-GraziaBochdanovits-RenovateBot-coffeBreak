package it.unibo.coffebreak.model.impl.entity.enemy;

import it.unibo.coffebreak.model.api.entity.BarrelTransformationObserver;
import it.unibo.coffebreak.model.impl.utility.Vector2D;
import java.util.List;
import java.util.Objects;

/**
 * Manages the transformation of barrels into fire entities when triggered.
 * Implements the {@link BarrelTransformationObserver} to handle barrel-to-fire conversions.
 * 
 * <p>Responsibilities:
 * <ul>
 *   <li>Removes transformed barrels from active enemies list</li>
 *   <li>Creates and adds new fire entities with modified velocity</li>
 *   <li>Maintains consistency of the active enemies list</li>
 * </ul>
 */
public final class BarrelManager implements BarrelTransformationObserver {

    private final List<Enemy> activeEnemies;

    /**
     * Creates a new BarrelManager with the specified list of active enemies.
     * 
     * @param activeEnemies the modifiable list of currently active enemies (not null)
     * @throws IllegalArgumentException if activeEnemies is null
     */
    public BarrelManager(final List<Enemy> activeEnemies) {
        this.activeEnemies = Objects.requireNonNull(activeEnemies, "Active enemies list cannot be null");
    }

    /**
     * Handles the transformation of a barrel into a fire entity.
     * 
     * <p>Execution flow:
     * <ol>
     *   <li>Removes the barrel from active enemies</li>
     *   <li>Creates a new fire entity with inverted velocity</li>
     *   <li>Adds the fire entity to active enemies</li>
     * </ol>
     * 
     * @param barrel the barrel that has been transformed (not null)
     * @param newVelocity the new velocity vector for the fire entity (not null)
     * @throws IllegalArgumentException if any parameter is null
     */
    @Override
    public void onBarrelTransformedToFire(final Barrel barrel, final Vector2D newVelocity) {
        Objects.requireNonNull(barrel, "Barrel cannot be null");
        Objects.requireNonNull(newVelocity, "NewVelocity cannot be null");
        activeEnemies.remove(barrel);
        final Fire fire = new Fire(barrel.getPosition(), barrel.getDimension(), newVelocity);
        activeEnemies.add(fire);
    }

    /**
     * Gets the current list of active enemies.
     * 
     * @return an unmodifiable view of the active enemies list
     */
    public List<Enemy> getActiveEnemies() {
        return List.copyOf(activeEnemies);
    }
}
