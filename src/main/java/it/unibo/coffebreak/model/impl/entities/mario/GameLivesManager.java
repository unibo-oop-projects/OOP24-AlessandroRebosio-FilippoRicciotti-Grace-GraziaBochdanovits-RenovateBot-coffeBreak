package it.unibo.coffebreak.model.impl.entities.mario;

import it.unibo.coffebreak.model.api.entities.character.LivesManager;

/**
 * A concrete implementation of {@link LivesManager} that manages the lives
 * of a character in the game, specifically for a Mario-like character.
 * It provides functionality to track, lose, and reset lives, as well as
 * determine if the game is over based on the remaining lives.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class GameLivesManager implements LivesManager {

    /** The initial number of lives assigned at the start or upon reset. */
    private static final int START_LIVES = 3;

    /** The current number of remaining lives. */
    private int lives;

    /**
     * Constructs a new {@code GameLivesManager} with the default initial number of lives
     * defined by {@code START_LIVES}.
     */
    public GameLivesManager() {
        this.lives = START_LIVES;
    }

    /**
     * {@inheritDoc}
     * 
     * @return the current number of lives remaining
     */
    @Override
    public int getLives() {
        return this.lives;
    }

    /**
     * {@inheritDoc}
     * Decrements the number of lives by one, if there are lives remaining.
     * If the lives count is already zero, this method has no effect.
     */
    @Override
    public void loseLife() {
        if (this.lives > 0) {
            this.lives--;
        }
    }

    /**
     * {@inheritDoc}
     * Resets the number of lives back to the initial value defined by {@code START_LIVES}.
     */
    @Override
    public void resetLives() {
        this.lives = START_LIVES;
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@code true} if there are lives remaining, {@code false} otherwise
     */
    @Override
    public boolean isAlive() {
        return this.lives > 0;
    }
}
