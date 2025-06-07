package it.unibo.coffebreak.api.model.entities;

/**
 * An interface that manages the lives of a character in the game.
 * It provides methods to handle the current number of lives, losing a life,
 * resetting lives, and checking if the game is over.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface LivesManager {
    /**
     * Returns the current number of lives of the character.
     *
     * @return the current number of lives
     */
    int getLives();

    /**
     * Decrements the number of lives by one.
     * This method should be called when the character loses a life.
     */
    void loseLife();

    /**
     * Resets the number of lives to the initial value.
     * This method should be called when starting a new game or level.
     */
    void resetLives();

    /**
     * Checks if the character is alive.
     * 
     * @return true if the character has lives remaining, false otherwise
     */
    boolean isAlive();
}
