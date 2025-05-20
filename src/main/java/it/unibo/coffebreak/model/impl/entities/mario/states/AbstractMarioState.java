package it.unibo.coffebreak.model.impl.entities.mario.states;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.states.CharacterState;

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
     * @return false by default as Mario cannot climb in basic states
     */
    @Override
    public boolean canClimb() {
        return false;
    }

    /**
     * Handles collision with another entity.
     * Must be implemented by concrete state classes to define state-specific behavior.
     * 
     * @param character the character involved in the collision (typically Mario)
     * @param other the other entity involved in the collision
     */
    @Override
    public abstract void handleCollision(Character character, Entity other);

    /**
     * {@inheritDoc}
     * Default empty implementation. Can be overridden by subclasses for state-specific behavior.
     * 
     * @param character the character entering this state
     */
    @Override
    public void onEnter(final Character character) {
    }

    /**
     * {@inheritDoc}
     * Default empty implementation. Can be overridden by subclasses for state-specific behavior.
     * 
     * @param character the character exiting this state
     */
    @Override
    public void onExit(final Character character) {
    }

    /**
     * {@inheritDoc}
     * Default empty implementation. Can be overridden by subclasses for state-specific updates.
     * 
     * @param character the character to update
     * @param deltaTime the time elapsed since last update
     */
    @Override
    public void update(final Character character, final float deltaTime) {
    }
}
