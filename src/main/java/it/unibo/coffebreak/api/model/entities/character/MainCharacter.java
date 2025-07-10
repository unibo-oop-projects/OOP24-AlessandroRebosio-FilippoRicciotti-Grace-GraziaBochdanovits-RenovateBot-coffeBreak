package it.unibo.coffebreak.api.model.entities.character;

import java.util.function.Supplier;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;

/**
 * Represents a character entity in the game world.
 * Extends the basic {@link Entity} interface to provide character-specific
 * functionality.
 * Characters are typically interactive entities that can move, have behaviors,
 * and interact with other game elements.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface MainCharacter extends Entity {

    /**
     * Changes Mario's current state using a state supplier.
     * 
     * @param stateSupplier supplier providing the new state
     */
    void changeState(Supplier<CharacterState> stateSupplier);

    /**
     * Increases the character's score by the specified amount.
     * 
     * @param amount the number of points to add to the character's score
     */
    void earnPoints(int amount);

    /**
     * Returns the current state of the character.
     *
     * @return the current {@link CharacterState} of this character
     */
    CharacterState getCurrentState();

    /**
     * Moves the character to the left.
     * This method handles leftward movement and may update the character's
     * position, velocity, and facing direction.
     */
    void moveLeft();

    /**
     * Moves the character to the right.
     * This method handles rightward movement and may update the character's
     * position, velocity, and facing direction.
     */
    void moveRight();

    /**
     * Moves the character upward.
     * This method handles upward movement, typically used for climbing
     * or vertical navigation.
     */
    void moveUp();

    /**
     * Moves the character downward.
     * This method handles downward movement, typically used for climbing
     * or vertical navigation.
     */
    void moveDown();

    /**
     * Makes the character perform a jump action.
     * This method initiates the jumping behavior, which may involve
     * changing the character's state, velocity, or animation.
     */
    void jump();

    /**
     * Method for get score value.
     * 
     * @return the score value
     */
    int getScoreValue();

    /**
     * Decrements the character's life count.
     */
    void loseLife();

    /**
     * Checks if the game is over, which typically happens when the number of lives
     * reaches zero.
     *
     * @return true if the game is over (no lives left), false otherwise
     */
    boolean isGameOver();

    /**
     * Gets the current number of lives the character has.
     * 
     * @return the number of lives remaining
     */
    int getLives();

    /**
     * Resets the number of lives back to the initial value defined.
     */
    void resetLives();

    /**
     * Returns whether Mario is currently facing right.
     * 
     * @return true if Mario is facing right, false if facing left
     */
    boolean isFacingRight();

    /**
     * Returns whether Mario is currently jumping.
     * 
     * @return true if Mario is jumping, false otherwise
     */
    boolean isJumping();

    /**
     * Returns whether the character is currently climbing.
     * This method indicates if the character is in a climbing state,
     * such as on a ladder or similar climbable structure.
     * 
     * @return true if the character is climbing, false otherwise
     */
    boolean isClimbing();
}
