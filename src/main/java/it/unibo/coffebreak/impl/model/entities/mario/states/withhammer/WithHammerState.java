package it.unibo.coffebreak.impl.model.entities.mario.states.withhammer;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.impl.model.entities.mario.states.AbstractMarioState;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;

/**
 * Represents Mario's state when equipped with a hammer.
 * In this state:
 * <ul>
 * <li>Mario can destroy barrels and other breakable entities on collision</li>
 * <li>Maintains normal movement capabilities</li>
 * <li>Automatically expires after {@value #HAMMER_DURATION} milliseconds</li>
 * <li>Allows use of special hammer attacks</li>
 * </ul>
 *
 * <p>
 * State transitions:
 * <ul>
 * <li>From {@link NormalState} when collecting a hammer power-up</li>
 * <li>To {@link NormalState} when:
 * <ul>
 * <li>Hammer duration expires</li>
 * <li>Mario loses a life</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class WithHammerState extends AbstractMarioState {

    /**
     * The duration in milliseconds that Mario keeps the hammer (5 seconds).
     */
    public static final long HAMMER_DURATION = 5000;

    /**
     * The system time in milliseconds when this hammer state will expire.
     */
    private long expirationTime;

    /**
     * Called when entering hammer state. Initializes:
     * <ul>
     * <li>Expiration timer ({@code currentTime + HAMMER_DURATION})</li>
     * </ul>
     *
     * @param character the Mario instance transitioning to this state (non-null)
     */
    @Override
    public void onEnter(final MainCharacter character) {
        this.expirationTime = System.currentTimeMillis() + HAMMER_DURATION;
    }

    /**
     * Checks if the hammer state has expired.
     *
     * @return true if the current time is past the expiration time, false otherwise
     */
    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() >= expirationTime;
    }

    /**
     * Updates hammer state logic.
     * <ul>
     * <li>Checks for expiration</li>
     * <li>Manages hammer attack cooldowns</li>
     * </ul>
     *
     * @param character the Mario instance being updated (non-null)
     * @param deltaTime the time elapsed since last frame (in seconds, positive)
     */
    @Override
    public void update(final MainCharacter character, final float deltaTime) {
        if (this.isExpired()) {
            character.changeState(NormalState::new);
        }
    }

    /**
     * Handles collisions with hammer effect.
     * <ul>
     * <li>Destroys barrels on contact</li>
     * <li>Damages enemies</li>
     * <li>Ignores non-breakable entities</li>
     * </ul>
     *
     * @param character the Mario instance involved in collision (non-null)
     * @param other     the colliding entity (non-null)
     */
    @Override
    public void handleCollision(final MainCharacter character, final Entity other) {
        if (other instanceof final Enemy enemy) {
            enemy.destroy();
        }
    }
}
