package it.unibo.coffebreak.model.api.entities.character;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Represents a state of a character in the game.
 * Defines the behavior and capabilities of the character when in this state.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface CharacterState {

    /**
     * Called when entering this state.
     * @param character the character changing state
     */
    void onEnter(Character character);

    /**
     * Called when exiting this state.
     * @param character the character changing state
     */
    void onExit(Character character);

    /**
     * Updates the character in this state.
     * @param character the character to update
     * @param deltaTime time since last update in seconds
     */
    void update(Character character, float deltaTime);

    /**
     * Handles collision with another entity while in this state.
     * @param character the character in this state
     * @param other the entity collided with
     */
    void handleCollision(Character character, Entity other);

    /**
     * @return true if the character can jump in this state
     */
    boolean canJump();

    /**
     * @return true if the character can use special items in this state
     */
    boolean canUseSpecialItem();

}
