package it.unibo.coffebreak.model.impl.entities.mario.states;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * Represents Mario's state when climbing ladders.
 * In this state:
 * <ul>
 *   <li>Gravity effects are disabled</li>
 *   <li>Vertical movement is controlled by player input</li>
 *   <li>Horizontal movement is disabled</li>
 *   <li>Transitions back to {@link NormalState} when leaving ladder or landing on platform</li>
 * </ul>
 *
 * <p>State transitions:
 * <ul>
 *   <li>From {@link NormalState} when colliding with ladder and pressing up/down</li>
 *   <li>To {@link NormalState} when:
 *     <ul>
 *       <li>Player releases climb input</li>
 *       <li>Mario reaches solid ground</li>
 *       <li>Mario leaves ladder area</li>
 *     </ul>
 *   </li>
 *   <li>To {@link DeadState} if health reaches zero</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class ClimbingState implements CharacterState {

    /**
     * Called when entering climbing state. Initializes climbing-specific properties:
     * <ul>
     *   <li>Resets vertical velocity</li>
     *   <li>Disables gravity effects</li>
     * </ul>
     *
     * @param character the Mario instance transitioning to this state (non-null)
     */
    @Override
    public void onEnter(final Character character) {
        character.setVelocity(new Vector2D(character.getVelocity().getX(), 0));
    }

    /**
     * Called when exiting climbing state. No specific cleanup needed.
     *
     * @param character the Mario instance leaving this state (non-null)
     */
    @Override
    public void onExit(final Character character) {
        // Intentionally left blank
    }

    /**
     * Updates climbing movement based on player input.
     * Should be called every frame to apply vertical movement.
     *
     * @param character the Mario instance being updated (non-null)
     * @param deltaTime the time elapsed since last frame (in seconds, positive)
     */
    @Override
    public void update(final Character character, final float deltaTime) {
    }

    /**
     * Handles collisions during climbing.
     * <ul>
     *   <li>Transitions to {@link NormalState} when touching solid platforms</li>
     *   <li>Maintains climbing state when touching ladders</li>
     * </ul>
     *
     * @param character the Mario instance involved in collision (non-null)
     * @param other the colliding entity (non-null)
     */
    @Override
    public void handleCollision(final Character character, final Entity other) {
        // Transition to NormalState when touching a platform
    }

    /**
     * @return false - horizontal movement is disabled while climbing
     */
    @Override
    public boolean canMove() {
        return false;
    }

    /**
     * @return false - jumping is disabled while climbing
     */
    @Override
    public boolean canJump() {
        return false;
    }

    /**
     * @return true - indicates Mario is currently climbing
     */
    @Override
    public boolean canClimb() {
        return true;
    }

    /**
     * @return false - special items cannot be used while climbing
     */
    @Override
    public boolean canUseSpecialItem() {
        return false;
    }
}
