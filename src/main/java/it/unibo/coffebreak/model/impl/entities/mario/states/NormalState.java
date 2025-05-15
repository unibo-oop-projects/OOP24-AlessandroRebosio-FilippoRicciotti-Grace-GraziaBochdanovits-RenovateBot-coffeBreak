package it.unibo.coffebreak.model.impl.entities.mario.states;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;

/**
 * Represents Mario's default state when he is on the ground and can move freely.
 * In this state, Mario can:
 * <ul>
 *   <li>Move horizontally</li>
 *   <li>Jump</li>
 *   <li>Start climbing if he collides with a ladder and presses up/down</li>
 * </ul>
 * 
 * @see CharacterState
 * @see Mario
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class NormalState implements CharacterState {

    /**
     * Called when entering this state.
     * 
     * @param character the Mario instance transitioning to this state
     */
    @Override
    public void onEnter(final Character character) {
    }

    /**
     * Called when exiting this state. No cleanup needed for this state.
     * 
     * @param character the Mario instance leaving this state
     */
    @Override
    public void onExit(final Character character) {
    }

    /**
     * Empty update method as no continuous behavior is needed in this state.
     * 
     * @param character the Mario instance being updated
     * @param deltaTime the time elapsed since last frame (in seconds)
     */
    @Override
    public void update(final Character character, final float deltaTime) {
    }

    /**
     * Handles collisions with other entities.
     * 
     * @param character the Mario instance involved in the collision
     * @param other the entity colliding with Mario
     */
    @Override
    public void handleCollision(final Character character, final Entity other) {
    }

    /**
     * @return true, as Mario can jump when in normal state
     */
    @Override
    public boolean canJump() {
        return true;
    }

    /**
     * @return false, as Mario needs to be in {@link WithHammerState} to use special items
     */
    @Override
    public boolean canUseSpecialItem() {
        return false;
    }
}
