package it.unibo.coffebreak.model.impl.entities.mario.states;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;

/**
 * Represents Mario's state when equipped with a hammer.
 * In this state:
 * <ul>
 *   <li>Mario can destroy barrels and other breakable entities on collision</li>
 *   <li>Maintains normal movement capabilities</li>
 *   <li>Automatically expires after {@value #HAMMER_DURATION} milliseconds</li>
 *   <li>Allows use of special hammer attacks</li>
 * </ul>
 *
 * <p>State transitions:
 * <ul>
 *   <li>From {@link NormalState} when collecting a hammer power-up</li>
 *   <li>To {@link NormalState} when:
 *     <ul>
 *       <li>Hammer duration expires</li>
 *       <li>Mario loses a life</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class WithHammerState implements CharacterState {

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
     *   <li>Expiration timer ({@code currentTime + HAMMER_DURATION})</li>
     * </ul>
     *
     * @param character the Mario instance transitioning to this state (non-null)
     */
    @Override
    public void onEnter(final Character character) {
        this.expirationTime = System.currentTimeMillis() + HAMMER_DURATION;
    }

    /**
     * Checks if the hammer state has expired.
     *
     * @return true if the current time is past the expiration time, false otherwise
     */
    public boolean isExpired() {
        return System.currentTimeMillis() >= expirationTime;
    }

    /**
     * Called when exiting hammer state.
     *
     * @param character the Mario instance leaving this state (non-null)
     */
    @Override
    public void onExit(final Character character) {
    }

    /**
     * Updates hammer state logic.
     * <ul>
     *   <li>Checks for expiration</li>
     *   <li>Manages hammer attack cooldowns</li>
     * </ul>
     *
     * @param character the Mario instance being updated (non-null)
     * @param deltaTime the time elapsed since last frame (in seconds, positive)
     */
    @Override
    public void update(final Character character, final float deltaTime) {
        if (isExpired()) {
            character.changeState(new NormalState());
        }
    }

    /**
     * Handles collisions with hammer effect.
     * <ul>
     *   <li>Destroys barrels on contact</li>
     *   <li>Damages enemies</li>
     *   <li>Ignores non-breakable entities</li>
     * </ul>
     *
     * @param character the Mario instance involved in collision (non-null)
     * @param other the colliding entity (non-null)
     */
    @Override
    public void handleCollision(final Character character, final Entity other) {
        if (other instanceof final Enemy enemy) {
            enemy.destroy();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canClimb() {
        return false;
    }

}
