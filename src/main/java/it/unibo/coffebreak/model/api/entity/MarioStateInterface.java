package it.unibo.coffebreak.model.api.entity;

import it.unibo.coffebreak.model.impl.entity.MarioState;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Defines the behavior interface for Mario's various states in the State Pattern implementation.
 * Each concrete state class implements these methods to provide state-specific behavior.
 * <p>
 * This interface follows the State design pattern to encapsulate different behaviors
 * for Mario depending on his current state (e.g., standing, jumping, climbing).
 * 
 * @see Mario
 */
public interface MarioStateInterface {

    /**
     * Calculates Mario's new position based on current state and movement direction.
     *
     * @param mario the Mario instance whose position should be updated, cannot be {@code null}
     * @param direction the movement direction vector, cannot be {@code null}
     * @return the new calculated position
     * @throws NullPointerException if either {@code mario} or {@code direction} is {@code null}
     */
    Position move(Mario mario, Vector2D direction);

    /**
     * Updates Mario's state-specific logic for each game frame.
     * <p>
     * This method is called every frame and should contain state-specific update logic,
     * such as timing for temporary power-ups, animation state changes, or other
     * time-dependent behavior.
     *
     * @param mario the Mario instance being updated, cannot be {@code null}
     * @param deltaTime the time elapsed since the last frame in milliseconds
     * @throws NullPointerException if {@code mario} is {@code null}
     * @throws IllegalArgumentException if {@code deltaTime} is negative
     */
    void update(Mario mario, long deltaTime);

    /**
     * Handles jump behavior specific to the current state.
     * Implementations may change Mario's state (e.g., to jumping state) and
     * apply appropriate physics for the jump.
     *
     * @param mario the Mario instance performing the jump, cannot be {@code null}
     * @throws NullPointerException if {@code mario} is {@code null}
     * @throws IllegalStateException if jumping is not allowed in current state
     */
    void jump(Mario mario);

    /**
     * Handles hammer usage behavior specific to the current state.
     * Implementations may determine if hammer can be used and apply
     * appropriate effects.
     *
     * @param mario the Mario instance using the hammer, cannot be {@code null}
     * @throws NullPointerException if {@code mario} is {@code null}
     * @throws IllegalStateException if hammer cannot be used in current state
     */
    void useHammer(Mario mario);

    /**
     * Handles climbing behavior specific to the current state.
     * Implementations may change Mario's state (e.g., to climbing state) and
     * apply appropriate movement constraints.
     *
     * @param mario the Mario instance attempting to climb, cannot be {@code null}
     * @param direction the climbing direction (positive for up, negative for down)
     * @throws NullPointerException if {@code mario} is {@code null}
     * @throws IllegalStateException if climbing is not allowed in current state
     */
    void climb(Mario mario, int direction);

    /**
     * Called when entering this state. Can be used for state initialization.
     * Default empty implementation.
     *
     * @param mario the Mario instance transitioning to this state, cannot be {@code null}
     */
    default void onStateEnter(Mario mario) { }

    /**
     * Called when exiting this state. Can be used for cleanup before state transition.
     * Default empty implementation.
     *
     * @param mario the Mario instance transitioning from this state, cannot be {@code null}
     */
    default void onStateExit(Mario mario) { }

    /**
     * Determines if Mario can climb in the current state.
     *
     * @return {@code true} if climbing is allowed in current state, {@code false} otherwise
     */
    boolean canClimb();

    /**
     * Determines if Mario can use the hammer in the current state.
     *
     * @return {@code true} if hammer usage is allowed in current state, {@code false} otherwise
     */
    boolean canUseHammer();

    /**
     * Identifies the formal state type.
     *
     * @return corresponding {@link MarioState} enum value
     */
    MarioState getStateType(); 
}
