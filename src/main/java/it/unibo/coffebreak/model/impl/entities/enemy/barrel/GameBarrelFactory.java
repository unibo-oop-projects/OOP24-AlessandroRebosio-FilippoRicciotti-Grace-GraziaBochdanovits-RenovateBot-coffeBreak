package it.unibo.coffebreak.model.impl.entities.enemy.barrel;

import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.BarrelFactory;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;

/**
 * A concrete implementation of {@link BarrelFactory} that creates {@link GameBarrel} instances
 * with default configurations for the Donkey Kong game.
 * 
 * <p>This factory provides pre-configured barrels with:
 * <ul>
 *   <li>Default dimensions</li>
 *   <li>Pre-set throwing velocity</li>
 *   <li>Optional fire transformation capability</li>
 * </ul>
 * 
 * @see BarrelFactory
 * @see GameBarrel
 * @author Grazia Bochdanovits de Kavna
 */
public class GameBarrelFactory implements BarrelFactory {

    /** The default dimensions for created barrels (1.0f x 1.0f). */
    private static final Dimension2D DEFAULT_BARREL_DIMENSION = new Dimension2D(1.0f, 1.0f);

    /** The horizontal velocity component for thrown barrels. */
    private static final float THROW_SPEED_X = 2.5f;

    /** The vertical velocity component for thrown barrels. */
    private static final float THROW_SPEED_Y = 3.0f;

    /**
     * Creates a new {@link GameBarrel} instance at the specified position with default properties.
     *
     * @param position the initial position of the barrel in 2D space
     * @param canTransformToFire whether the barrel can transform into a fire barrel during gameplay
     * @return a new {@link GameBarrel} instance
     */
    @Override
    public Barrel createBarrel(final Position2D position, final boolean canTransformToFire) {
        final GameBarrel barrel = new GameBarrel(position, DEFAULT_BARREL_DIMENSION, canTransformToFire);
        barrel.setVelocity(new Vector2D(THROW_SPEED_X, THROW_SPEED_Y));
        return barrel;
    }
}
