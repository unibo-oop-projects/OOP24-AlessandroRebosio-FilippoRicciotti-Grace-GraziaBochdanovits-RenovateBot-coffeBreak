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
public interface Character extends Entity {
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
    void increaseScore(int amount);

    /**
     * Gets the current score value.
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
