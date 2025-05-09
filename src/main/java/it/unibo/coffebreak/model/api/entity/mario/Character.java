package it.unibo.coffebreak.model.api.entity.mario;

import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Represents a playable character in the game.
 */
public interface Character {

    /**
     * Makes the character jump.
     */
    void jump();

    /**
     * Reduces the character's life.
     */
    void loseLife();

    /**
     * Return a flag if the character is on a platform.
     * @return true if the charcter is on platform
     */
    boolean isOnPlatform();

    void changeState(/*objectState */);

    Vector2D getVelocity();

    void setVelocity(Vector2D velocity);

    boolean isFacingRight();

    void setFacingRight();
}

