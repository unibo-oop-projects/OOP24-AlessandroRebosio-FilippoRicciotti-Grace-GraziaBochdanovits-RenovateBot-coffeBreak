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
 * <p>This state is automatically activated when:
 * <ul>
 *   <li>Mario lands on a platform</li>
 *   <li>Mario exits from {@link ClimbingState} or {@link JumpState}</li>
 *   <li>The game resets</li>
 * </ul>
 * 
 * @see CharacterState
 * @see Mario
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class NormalState implements CharacterState {

    /**
     * Called when entering this state. Resets Mario's ground status.
     * 
     * @param character the Mario instance transitioning to this state
     */
    @Override
    public void onEnter(final Character character) {
        character.setOnGround(true);
    }

    /**
     * Called when exiting this state. No cleanup needed for this state.
     * 
     * @param character the Mario instance leaving this state
     */
    @Override
    public void onExit(final Character character) {
        // Intentionally left blank
    }

    /**
     * Empty update method as no continuous behavior is needed in this state.
     * 
     * @param character the Mario instance being updated
     * @param deltaTime the time elapsed since last frame (in seconds)
     */
    @Override
    public void update(final Character character, final float deltaTime) {
        // No continuous behavior required in normal state
    }

    /**
     * Handles collisions with other entities.
     * <ul>
     *   <li>If colliding with a ladder and pressing up/down, transitions to {@link ClimbingState}</li>
     *   <li>Other collision types are handled by Mario's default behavior</li>
     * </ul>
     * 
     * @param character the Mario instance involved in the collision
     * @param other the entity colliding with Mario
     */
    @Override
    public void handleCollision(final Character character, final Entity other) {
        // Transition to ClimbingState when touching a ladder and pressing up/down
    }

    /**
     * @return true, as Mario can always move in this state
     */
    @Override
    public boolean canMove() {
        return true;
    }

    /**
     * @return true, as Mario can jump when in normal state
     */
    @Override
    public boolean canJump() {
        return true;
    }

    /**
     * @return false, as Mario needs to be in {@link ClimbingState} to climb
     */
    @Override
    public boolean canClimb() {
        return false;
    }

    /**
     * @return false, as Mario needs to be in {@link WithHammerState} to use special items
     */
    @Override
    public boolean canUseSpecialItem() {
        return false;
    }
}
