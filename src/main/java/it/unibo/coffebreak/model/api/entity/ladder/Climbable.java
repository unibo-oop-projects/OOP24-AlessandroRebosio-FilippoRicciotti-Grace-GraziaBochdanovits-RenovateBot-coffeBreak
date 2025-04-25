package it.unibo.coffebreak.model.api.entity.ladder;

/**
 * Represents an entity that can be climbed, such as a ladder.
 * Implementing classes should define whether the entity is currently climbable 
 * and the speed at which it can be climbed.
 */
public interface Climbable {

    /**
     * Checks if this entity can be climbed at the current moment.
     * 
     * @return {@code true} if the entity can be climbed, {@code false} otherwise.
     */
    boolean canClimb();

    /**
     * Gets the climbing speed associated with this entity.
     * The value represents how fast an entity (e.g., a player) can climb it.
     * 
     * @return a positive float value representing the climb speed. 
     *         Higher values mean faster climbing.
     */
    float getClimbSpeed();
}
