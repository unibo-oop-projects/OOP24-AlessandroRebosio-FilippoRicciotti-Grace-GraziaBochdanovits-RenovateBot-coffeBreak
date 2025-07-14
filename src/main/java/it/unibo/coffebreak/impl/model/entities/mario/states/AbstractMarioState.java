package it.unibo.coffebreak.impl.model.entities.mario.states;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;

/**
 * Abstract base class for Mario's state implementations.
 * Provides default behavior for Mario's states in the game.
 * Concrete subclasses should implement state-specific collision handling.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractMarioState implements CharacterState {
    /**
     * {@inheritDoc}
     * Default empty implementation. Can be overridden by subclasses for
     * state-specific behavior.
     * 
     * @param character the character entering this state
     */
    @Override
    public void onEnter(final MainCharacter character) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     * Default empty implementation. Can be overridden by subclasses for
     * state-specific behavior.
     * 
     * @param character the character exiting this state
     */
    @Override
    public void onExit(final MainCharacter character) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     * Default empty implementation. Can be overridden by subclasses for
     * state-specific updates.
     * 
     * @param character the character to update
     * @param deltaTime the time elapsed since last update
     */
    @Override
    public void update(final MainCharacter character, final float deltaTime) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     * 
     * @return false by default as Mario cannot climb in basic states
     */
    @Override
    public boolean canClimb() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canJump() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean didDesoyedEnemy() {
        return false;
    }

    /**
     * Handles collision with another entity.
     * Must be implemented by concrete state classes to define state-specific
     * behavior.
     * 
     * @param character the character involved in the collision (typically Mario)
     * @param other     the other entity involved in the collision
     */
    @Override
    public abstract void handleCollision(MainCharacter character, Entity other);
}
