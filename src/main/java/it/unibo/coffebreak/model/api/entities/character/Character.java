package it.unibo.coffebreak.model.api.entities.character;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.impl.entities.mario.GameLivesManager;
import it.unibo.coffebreak.model.impl.score.manager.GameScoreManager;

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
    void jump(); // TODO: add move() method 

    /**
     * Starts the climbing action for the character.
     */
    void startClimbing(); // TODO: ha senso?

    /**
     * Stops the climbing action for the character.
     */
    void stopClimbing(); // TODO: ha senso?

    /**
     * Uses a special item (generalized for hammer usage).
     */
    void useSpecialItem(); // TODO: ha senso?

    /**
     * Collects an item and applies its effects to the character.
     * 
     * @param item the collectible item to be collected
     */
    void collectItem(Collectible item); // TODO: remove this method, on collision check if entity is instaceof
                                        // final Collectible item and call item.collect(this);

    /**
     * Decrements the character's life count.
     */
    void loseLife();

    /**
     * Checks if the character is currently jumping.
     * 
     * @return true if the character is jumping, false otherwise
     */
    boolean isJumping();

    /**
     * Checks if the character is currently climbing.
     * 
     * @return true if the character is climbing, false otherwise
     */
    boolean isClimbing();

    /**
     * Checks if the character has the ability to climb.
     * 
     * @return true if the character can climb, false otherwise
     */
    boolean canClimb();

    /**
     * Checks if the character is currently on the ground.
     * 
     * @return true if the character is on the ground, false otherwise
     */
    boolean isOnGround();

    /**
     * Checks if the character is alive.
     * 
     * @return true if the character has lives remaining, false otherwise
     */
    boolean isAlive(); // TODO: rename in GameOver

    /**
     * Gets the lives manager for the character.
     * 
     * @return the GameLivesManager instance managing the character's lives
     */
    GameLivesManager getLivesManager(); // TODO: change type in LivesManager

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
    GameScoreManager getScoreManager(); // TODO: change type in ScoreManager

    /**
     * Gets the name of the player controlling this character.
     * 
     * @return the player's name
     */
    String getPlayerName();

    /**
     * Gets the current state of the character.
     * 
     * @return the current CharacterState
     */
    CharacterState getCharacterState();

    /**
     * Sets whether the character is on the ground.
     * 
     * @param onGround true to set the character as on the ground, false otherwise
     */
    void setOnGround(boolean onGround);

}
