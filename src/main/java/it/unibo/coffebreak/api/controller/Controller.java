package it.unibo.coffebreak.api.controller;

import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.model.states.GameState;

/**
 * The game controller in the MVC (Model-View-Controller) pattern.
 * <p>
 * Acts as the intermediary between the view (user input) and the model
 * (game logic). Handles input processing and coordinates model updates.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {
    /**
     * Handles a key press event by forwarding it to the input system.
     * 
     * @param keyCode the key code of the pressed key
     */
    void keyPressed(int keyCode);

    /**
     * Handles a key release event by forwarding it to the input system.
     * 
     * @param keyCode the key code of the released key
     */
    void keyReleased(int keyCode);

    List<Entity> getEntities();

    int getScoreValue();

    int getBonusValue();

    List<Entry> getLeaderBoard();

    int getHighestScore();

    int getLevelIndex();

    GameState getGameState();

    /**
     * Processes all pending input commands and applies them to the game model.
     * Should be called once per frame to ensure responsive controls.
     */
    void processInput();

    /**
     * Updates the game model based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    void updateModel(float deltaTime);

    /**
     * Checks if the game should continue running.
     * 
     * @return true if the game should continue running, false otherwise
     */
    boolean isGameActive();
}
