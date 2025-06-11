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
     * Sets the movement direction of the character.
     *
     * @param dir the direction in which to move the character.
     *            The specific values and their meanings should be defined in the
     *            implementing class or documentation.
     */
    void setMoveDirection(int dir);

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
}
