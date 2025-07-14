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
     * @return true if the character can climb in this state
     */
    boolean canClimb();

    /**
     * @return true if the character can jump in this state
     */
    boolean canJump();

    /**
     * Returns whether the character has destroyed an enemy in this state.
     *
     * @return true if an enemy was destroyed in this state, false otherwise
     */
    boolean didDesoyedEnemy();

    /**
     * Handles collision with another entity while in this state.
     * 
     * @param character the character in this state
     * @param other     the entity collided with
     */
    void handleCollision(MainCharacter character, Entity other);
}
