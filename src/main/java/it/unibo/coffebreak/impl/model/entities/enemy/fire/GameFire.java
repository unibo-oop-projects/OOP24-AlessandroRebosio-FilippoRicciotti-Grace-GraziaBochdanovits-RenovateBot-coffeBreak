package it.unibo.coffebreak.impl.model.entities.enemy.fire;

import java.util.Random;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.enemy.AbstractEnemy;

/**
 * Represents a fire entity in the game, which is a specific type of enemy.
 * 
 * @see Fire
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameFire extends AbstractEnemy implements Fire {

    private static final float FIRE_SPEED = 20f;
    private static final float CLIMB_PROBABILITY = 0.3f;
    private static final float CHANGE_DIRECTION_INTERVAL = 2.0f;
    private static final float DEFAULT_LIFETIME = 15.0f;

    private final Random random = new Random();
    private float directionChangeElapsed;
    private float lifeElapsed;
    private boolean climbing;
    private boolean ladderCollision;

    /**
     * Constructs a new GameFire with the specified position and dimensions.
     *
     * @param position  the initial position of the fire in 2D space
     * @param dimension the dimension of the fire in the game world
     */
    public GameFire(final Position position, final BoundigBox dimension) {
        super(position, dimension);

        this.setVelocity(new Vector(FIRE_SPEED, 0f));
    }

    /**
     * Defines the behavior when this fire entity collides with another entity.
     *
     * @param other the entity that collided with this fire
     */
    @Override
    public void onCollision(final Entity other) {
        switch (other) {
            case final Platform platform -> {
                this.onPlatformLand();
                if (!ladderCollision) {
                    this.climbing = false;
                }
            }
            case final Ladder ladder -> {
                this.ladderCollision = true;
                    if (random.nextFloat() < CLIMB_PROBABILITY) {
                        this.lifeElapsed = 0f;
                        this.climbing = true;
                        this.onPlatformLeave();
                    }
            }
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        ladderCollision = false;
        directionChangeElapsed += deltaTime;
        lifeElapsed += deltaTime;

        if (lifeElapsed >= DEFAULT_LIFETIME) {
            destroy();
            return;
        }
        if (climbing) {
            setVelocity(new Vector(0f, -FIRE_SPEED));
            return;
        }
        if (isOnPlatform()) {
            if (directionChangeElapsed >= CHANGE_DIRECTION_INTERVAL) {
                if (random.nextBoolean()) {
                    invertDirection();
                }
                directionChangeElapsed = 0f;
            }
            setVelocity(new Vector(getHorizontalSpeed(FIRE_SPEED), 0f));
        } else {
            setVelocity(new Vector(0f, FIRE_SPEED));
        }
    }
}
