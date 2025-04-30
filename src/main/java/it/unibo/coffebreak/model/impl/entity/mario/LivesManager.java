package it.unibo.coffebreak.model.impl.entity.mario;

/**
 * A utility class responsible for managing the number of lives of the playable character.
 * It provides methods to decrement, reset, and query the current number of lives,
 * as well as to determine if the game is over.
 */
public class LivesManager {

    /** The initial number of lives assigned at the start or upon reset. */
    private static final int START_LIVES = 3;

    /** The current number of remaining lives. */
    private int lives;

    /**
     * Constructs a new {@code LivesManager} with the specified initial number of lives.
     */
    public LivesManager() {
        this.lives = START_LIVES;
    }

    /**
     * Decreases the number of lives by one, if at least one life remains.
     * If the number of lives is already zero, this method has no effect.
     */
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    /**
     * Resets the number of lives to the initial value.
     */
    public void reset() {
        lives = START_LIVES;
    }

    /**
     * Returns the current number of lives remaining.
     *
     * @return the current life count.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Determines whether the game is over, i.e., the number of lives is zero or less.
     *
     * @return {@code true} if no lives remain; {@code false} otherwise.
     */
    public boolean isGameOver() {
        return lives <= 0;
    }
}
