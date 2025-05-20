package it.unibo.coffebreak.model.api.entities.character;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.states.CharacterState;
import it.unibo.coffebreak.model.api.score.ScoreManager;

/**
 * Represents a character entity in the game world.
 * Extends the basic {@link Entity} interface to provide character-specific functionality.
 * Characters are typically interactive entities that can move, have behaviors,
 * and interact with other game elements.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface Character extends Entity {

    /**
     * Changes the current state of the character to the specified new state.
     * 
     * @param newState the new state to set for the character
     */
    void changeState(CharacterState newState);

    /**
     * Decrements the character's life count.
     */
    void loseLife();

    /**
     * Checks if the character is currently on the ground.
     * 
     * @return true if the character is on the ground, false otherwise
     */
    boolean isOnGround();

    /**
     * Checks if the game is over, which typically happens when the number of lives reaches zero.
     *
     * @return true if the game is over (no lives left), false otherwise
     */
    boolean isGameOver();

    /**
     * Gets the score manager for the character.
     * 
     * @return the GameScoreManager instance managing the character's score
     */
    ScoreManager getScoreManager();

    /**
     * Gets the current number of lives the character has.
     * 
     * @return the number of lives remaining
     */
    int getLives();

    /**
     * Gets the current score value of the player.
     * 
     * @return the score value
     */
    int getScore();
}
