package it.unibo.coffebreak.model.impl.entities.mario.states;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;

/**
 * Represents Mario's state when performing a jump.
 * In this state:
 * <ul>
 *   <li>Mario is affected by gravity</li>
 *   <li>Horizontal movement is allowed but restricted</li>
 *   <li>Cannot perform additional jumps until landing</li>
 *   <li>Transitions back to {@link NormalState} upon landing</li>
 * </ul>
 *
 * <p>State transitions:
 * <ul>
 *   <li>From {@link NormalState} when jump is initiated</li>
 *   <li>To {@link NormalState} when touching ground</li>
 *   <li>To {@link DeadState} if health reaches zero</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class JumpState implements CharacterState {

    /**
     * Called when entering jump state.
     *
     * @param character the Mario instance transitioning to this state
     */
    @Override
    public void onEnter(final Character character) {
        // Intentionally left blank
    }

    /**
     * Called when exiting jump state. No cleanup typically needed.
     *
     * @param character the Mario instance leaving this state
     */
    @Override
    public void onExit(final Character character) {
        // Intentionally left blank
    }

    /**
     * Empty update as physics are handled by {@link Mario}'s update method.
     * Could be used for mid-air control adjustments.
     *
     * @param character the Mario instance being updated
     * @param deltaTime the time elapsed since last frame (in seconds)
     */
    @Override
    public void update(final Character character, final float deltaTime) {
        // Could implement air resistance or other mid-air effects
    }

    /**
     * Handles collisions during jump.
     * <ul>
     *   <li>Transitions to {@link NormalState} when landing on platforms</li>
     *   <li>Ignores collisions from sides/below</li>
     * </ul>
     *
     * @param character the Mario instance involved in collision
     * @param other the colliding entity
     */
    @Override
    public void handleCollision(final Character character, final Entity other) {
        // Transition to NormalState when touching a platform
    }

    /**
     * @return true - allows limited air control during jump
     */
    @Override
    public boolean canMove() {
        return true;
    }

    /**
     * @return false - prevents double jumping
     */
    @Override
    public boolean canJump() {
        return false;
    }

    /**
     * @return false - cannot initiate climbing mid-air
     */
    @Override
    public boolean canClimb() {
        return false;
    }

    /**
     * @return false - cannot use items during jump (or true for special cases)
     */
    @Override
    public boolean canUseSpecialItem() {
        return false;
    }
}
