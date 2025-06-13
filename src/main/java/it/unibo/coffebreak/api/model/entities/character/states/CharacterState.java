package it.unibo.coffebreak.api.model.entities.character.states;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Represents a state of a character in the game.
 * Defines the behavior and capabilities of the character when in this state.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface CharacterState {
    /**
     * Called when entering this state.
     * 
     * @param character the character changing state
     */
    void onEnter(MainCharacter character);

    /**
     * Called when exiting this state.
     * 
     * @param character the character changing state
     */
    void onExit(MainCharacter character);

    /**
     * Updates the character in this state.
     * 
     * @param character the character to update
     * @param deltaTime time since last update in seconds
     */
    void update(MainCharacter character, float deltaTime);

    /**
     * Checks if the hammer state has expired.
     *
     * @return true if the current time is past the expiration time, false otherwise
     */
    boolean isExpired();

    /**
     * @return true if the character can climb in this state
     */
    boolean canClimb();

    /**
     * @return true if the character is on a ladder
     */
    boolean isClimbing();

    /**
     * Called when Mario stops climbing (e.g., jumps off or moves away).
     */
    void stopClimbing();

    /**
     * Handles collision with another entity while in this state.
     * 
     * @param character the character in this state
     * @param other     the entity collided with
     */
    void handleCollision(MainCharacter character, Entity other);
}
