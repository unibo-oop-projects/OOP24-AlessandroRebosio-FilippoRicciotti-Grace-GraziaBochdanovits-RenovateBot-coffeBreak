package it.unibo.coffebreak.model.api.entities.character;

import it.unibo.coffebreak.model.api.entities.Entity;
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
     * Resets the character to its initial state.
     */
    void resetToInitialState();

    /**
     * Makes the character perform a jump action.
     */
    void jump();

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
     * Gets the lives manager for the character.
     * 
     * @return the GameLivesManager instance managing the character's lives
     */
    LivesManager getLivesManager();

    /**
     * Gets the current number of lives the character has.
     * 
     * @return the number of lives remaining
     */
    int getLives();

    /**
     * Gets the score manager for the character.
     * 
     * @return the GameScoreManager instance managing the character's score
     */
    ScoreManager getScoreManager();

    /**
     * Gets the name of the player controlling this character.
     * 
     * @return the player's name
     */
    String getPlayerName();

    /**
     * Sets whether the character is on the ground.
     * 
     * @param onGround true to set the character as on the ground, false otherwise
     */
    void setOnGround(boolean onGround);

}
