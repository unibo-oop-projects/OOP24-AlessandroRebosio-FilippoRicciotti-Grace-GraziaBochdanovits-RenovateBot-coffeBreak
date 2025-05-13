package it.unibo.coffebreak.model.impl.entities.mario.states;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;

/**
 * Represents Mario's state when he has no remaining lives (game over).
 * In this state:
 * <ul>
 *   <li>All movements and actions are disabled</li>
 *   <li>Triggers game over sequence on entry</li>
 *   <li>Ignores all collisions and updates</li>
 * </ul>
 *
 * <p>This is a terminal state - Mario cannot recover from death without external reset.
 * The state machine should be reinitialized by the game controller after showing game over screens.
 *
 * @see CharacterState
 * @see Mario
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class DeadState implements CharacterState {

    /**
     * Called when entering this state. Handles game over logic:
     * <ul>
     *   <li>Notifies the score manager to finalize the game session</li>
     *   <li>Should typically be followed by game over screen display</li>
     * </ul>
     *
     * @param character the Mario instance transitioning to this state (non-null)
     * @throws NullPointerException if character is null
     */
    @Override
    public void onEnter(final Character character) {
        Objects.requireNonNull(character, "Mario instance cannot be null");
        character.getScoreManager().endGame(character.getPlayerName());
    }

    /**
     * Called when exiting this state. Intentionally left blank as:
     * <ul>
     *   <li>This is typically a terminal state</li>
     *   <li>Any reset should be handled by game controller</li>
     * </ul>
     *
     * @param character the Mario instance leaving this state
     */
    @Override
    public void onExit(final Character character) {
        // No action - State transitions should be managed by game controller
        // Avoid resetToInitialState() here to prevent unintended revivals
    }

    /**
     * Empty update method - Mario should remain static during death.
     *
     * @param character the Mario instance being updated
     * @param deltaTime the time elapsed since last frame (in seconds)
     */
    @Override
    public void update(final Character character, final float deltaTime) {
        // Intentionally left blank
    }

    /**
     * Ignores all collisions while dead.
     *
     * @param character the Mario instance involved in collision
     * @param other the colliding entity
     */
    @Override
    public void handleCollision(final Character character, final Entity other) {
        // Dead Mario doesn't react to collisions
    }

    /**
     * @return false - Movement is always disabled in death state
     */
    @Override
    public boolean canMove() {
        return false;
    }

    /**
     * @return false - Jumping is always disabled in death state
     */
    @Override
    public boolean canJump() {
        return false;
    }

    /**
     * @return false - Climbing is always disabled in death state
     */
    @Override
    public boolean canClimb() {
        return false;
    }

    /**
     * @return false - Special items cannot be used in death state
     */
    @Override
    public boolean canUseSpecialItem() {
        return false;
    }
}
