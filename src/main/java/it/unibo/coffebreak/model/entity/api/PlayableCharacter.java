package it.unibo.coffebreak.model.entity.api;

/**
 * Represents a playable character in the game.
 */
public interface PlayableCharacter {

    /**
     * Makes the character jump.
     */
    void jump();

    /**
     * Reduces the character's life.
     */
    void loseLife();
}

