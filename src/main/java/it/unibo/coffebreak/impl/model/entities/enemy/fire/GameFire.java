package it.unibo.coffebreak.impl.model.entities.enemy.fire;

import java.util.Random;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.PhysicsEntity;
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
public class GameFire extends AbstractEnemy implements Fire, PhysicsEntity {

    private static final float FIRE_SPEED = 20f;
    private static final float CLIMB_PROBABILITY = 0.3f;
    private static final float CHANGE_DIRECTION_INTERVAL = 2.0f;
    private static final float CENTER_THRESHOLD = 0.5f;

    private final Random random = new Random();
    private float elapsedTime;
    private boolean onPlatform;
    private boolean climbing;
    private boolean ladderCollision;
    private boolean movingRight = true;

    /**
     * Constructs a new GameFire with the specified position and dimensions.
     *
     * @param position  the initial position of the fire in 2D space
     * @param dimension the dimension of the fire in the game world
     */
    public GameFire(final Position position, final BoundigBox dimension) {
        super(position, dimension);

        this.onPlatform = true;
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
                this.onPlatform = true;
                if (!ladderCollision) {
                    this.climbing = false;
                }
            }
            case final Ladder ladder -> {
                if (isCenteredOnLadder(ladder)) {
                    this.ladderCollision = true;
                    if (random.nextFloat() < CLIMB_PROBABILITY) {
                        this.climbing = true;
                        this.onPlatform = false;
                    }
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

        elapsedTime += deltaTime;

        if (this.climbing) {
            this.setVelocity(new Vector(0f, -FIRE_SPEED));
            return;
        }

        if (this.onPlatform) {
            if (elapsedTime >= CHANGE_DIRECTION_INTERVAL) {
                if (random.nextBoolean()) {
                    movingRight = !movingRight;
                }
                elapsedTime = 0f;
            }
            final float horizontalSpeed = movingRight ? FIRE_SPEED : -FIRE_SPEED;
            this.setVelocity(new Vector(horizontalSpeed, 0f));
        } else {
            this.setVelocity(new Vector(0f, FIRE_SPEED));
        }
    }

    private boolean isCenteredOnLadder(final Entity ladder) {
        final float ladderCenter = ladder.getPosition().x() + ladder.getDimension().width() / 2f;
        final float fireCenter = this.getPosition().x() + this.getDimension().width() / 2f;
        final float distance = Math.abs(fireCenter - ladderCenter);
        final float maxDistance = ladder.getDimension().width() * CENTER_THRESHOLD;
        return distance <= maxDistance;
    }
}
