package it.unibo.coffebreak.api.controller;

import java.util.List;
import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.model.states.ModelState;

/**
 * The game controller in the MVC (Model-View-Controller) pattern.
 * <p>
 * Acts as the intermediary between the view (user input) and the model
 * (game logic). Handles input processing and coordinates model updates.
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {
    /**
     * Handles a key press event by forwarding it to the input system.
     * Concrete implementations should translate the key code into appropriate game
     * actions.
     * 
     * @param keyCode the key code of the pressed key
     */
    void keyPressed(int keyCode);

    /**
     * Handles a key release event by forwarding it to the input system.
     * Important for stopping continuous actions like movement.
     * 
     * @param keyCode the key code of the released key
     */
    void keyReleased(int keyCode);

    /**
     * Processes all pending input commands and applies them to the game model.
     * Should be called once per frame to ensure responsive controls.
     * <p>
     * Implementation should:
     * </p>
     * <ol>
     * <li>Poll the input system for pending commands</li>
     * <li>Apply each command to the model</li>
     * <li>Clear processed commands</li>
     * </ol>
     */
    void processInput();

    /**
     * Updates the game model based on the elapsed time.
     * This drives the game simulation forward and should be called
     * once per frame with the actual frame time delta.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    void updateModel(float deltaTime);

    /**
     * Checks if the game should continue running.
     * Typically delegates to the model's game state.
     * 
     * @return true if the game is active and should continue running, false
     *         otherwise
     */
    boolean isGameActive();

    /**
     * Gets the current list of game entities to be rendered.
     * 
     * @return an unmodifiable list of game entities, never null
     */
    List<Entity> getEntities();

    /**
     * Gets the player character.
     * 
     * @return the main character instance, never null
     */
    Optional<MainCharacter> getMainCharacter();

    /**
     * Gets the current player score.
     * 
     * @return the current score value
     */
    int getScoreValue();

    /**
     * Gets the current bonus value (if applicable).
     * 
     * @return the current bonus value
     */
    int getBonusValue();

    /**
     * Gets the current leaderboard data.
     * 
     * @return an unmodifiable list of leaderboard entries, sorted by score (highest
     *         first)
     */
    List<Entry> getLeaderBoard();

    /**
     * Gets the highest score currently in the leaderboard.
     * 
     * @return the highest score value
     * @throws IllegalStateException if the leaderboard is empty
     */
    int getHighestScore();

    /**
     * Gets the current level index.
     * 
     * @return the current level identifier
     */
    int getLevelIndex();

    /**
     * Retrieves the current number of lives remaining for the character.
     *
     * @return the number of lives the character has left
     */
    int getCharacterLives();

    /**
     * Gets the current game state.
     * 
     * @return the current game state, never null
     */
    ModelState getGameState();
}
