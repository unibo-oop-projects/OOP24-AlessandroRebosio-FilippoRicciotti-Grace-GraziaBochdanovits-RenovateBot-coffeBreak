package it.unibo.coffebreak.model.api.entities.character;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.physics.Physics;
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
     * 
     * @param deltaTime time since last frame in seconds
     */
    void jump(float deltaTime);

    /**
     * Moves the character left at standard movement speed.
     *
     * @param deltaTime time since last frame in seconds
     */
    void moveLeft(float deltaTime);

    /**
     * Moves the character right at standard movement speed.
     *
     * @param deltaTime time since last frame in seconds
     */
    void moveRight(float deltaTime);

    /**
     * Makes the character climb upwards when on a ladder.
     *
     * @param deltaTime time since last frame in seconds
     */
    void climbUp(float deltaTime);

    /**
     * Makes the character climb downwards when on a ladder.
     *
     * @param deltaTime time since last frame in seconds
     */
    void climbDown(float deltaTime);

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
     * Gets the physics controller responsible for movement calculations.
     *
     * @return the Physics implementation for this character
     */
    Physics getPlayerPhysics();

    /**
     * Gets the current character State.
     * 
     * @return the current character state
     */
    CharacterState getCurrentState();

}
