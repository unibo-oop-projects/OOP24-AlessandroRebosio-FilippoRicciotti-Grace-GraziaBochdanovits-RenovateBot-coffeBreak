package it.unibo.coffebreak.api.model.entities.character.states;

import it.unibo.coffebreak.api.common.State;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Represents a state of a character in the game.
 * Defines the behavior and capabilities of the character when in this state.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface CharacterState extends State<MainCharacter> {

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
     * Handles collision with another entity while in this state.
     * 
     * @param character the character in this state
     * @param other     the entity collided with
     */
    void handleCollision(MainCharacter character, Entity other);
}
