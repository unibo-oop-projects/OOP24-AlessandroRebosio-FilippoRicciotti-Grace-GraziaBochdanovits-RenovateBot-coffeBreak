package it.unibo.coffebreak.model.impl.entity.mario;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entity.mario.MarioStateInterface;
import it.unibo.coffebreak.model.impl.entity.mario.state.DeadState;
import it.unibo.coffebreak.model.impl.entity.mario.state.NormalState;
import it.unibo.coffebreak.model.impl.entity.mario.state.WithHammerState;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Abstract base class implementing Mario's state behavior in the State Pattern.
 * <p>
 * Provides default implementations for:
 * <ul>
 *   <li>Basic movement physics</li>
 *   <li>Jump mechanics with {@link #JUMP_FORCE} constant</li>
 *   <li>Ladder climbing with {@link #CLIMB_SPEED}</li>
 *   <li>Null-safety checks for all operations</li>
 * </ul>
 * 
 * <p>Concrete states should override methods to implement state-specific behavior.
 * 
 * @see MarioStateInterface
 * @see NormalState
 * @see WithHammerState
 * @see DeadState
 */
public abstract class AbstractMarioState implements MarioStateInterface {

    /** 
     * The constant vertical speed at which Mario climbs ladders, in pixels per frame. 
     */
    protected static final int CLIMB_SPEED = 5;

    /** 
     * The constant upward force applied when Mario jumps, in pixels per frame. 
     */
    protected static final int JUMP_FORCE = 10;

    /**
     * Constructs a new AbstractMarioState for the specified Mario instance.
     *
     * @param mario the Mario instance this state belongs to, cannot be null
     * @throws NullPointerException if {@code mario} is null
     */
    public AbstractMarioState(final Mario mario) {
        Objects.requireNonNull(mario, "Mario instance cannot be null");
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation calculates the new position by adding the direction vector
     * to Mario's current position, without any state-specific modifications.
     *
     * @throws NullPointerException if either {@code mario} or {@code direction} is null
     */
    @Override
    public Position move(final Mario mario, final Vector2D direction) {
        Objects.requireNonNull(mario);
        Objects.requireNonNull(direction);
        return new Position(mario.getPosition().x() + direction.getX(),
                            mario.getPosition().y() + direction.getY());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Base implementation does nothing. Concrete states should override this to implement
     * state-specific behavior that needs to be updated each frame.
     * 
     * @param mario the Mario instance being updated, cannot be null
     * @param deltaTime the time elapsed since last frame in milliseconds
     * @throws NullPointerException if {@code mario} is null
     * @throws IllegalArgumentException if {@code deltaTime} is negative
     */
    @Override
    public void update(final Mario mario, final long deltaTime) {
        Objects.requireNonNull(mario);
        if (deltaTime < 0) {
            throw new IllegalArgumentException("Delta time cannot be negative");
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation applies an upward force to Mario's velocity if he's not already jumping.
     * Sets the {@link #isJumping} flag to true when initiating a jump.
     *
     * @throws NullPointerException if {@code mario} is null
     */
    @Override
    public void jump(final Mario mario) {
        Objects.requireNonNull(mario);
        if (mario.isOnGround()) {
            mario.setVelocity(new Vector2D(mario.getVelocity().getX(), -JUMP_FORCE));
        }
        mario.setOnGround(false);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation changes Mario's vertical position based on the climbing direction
     * and the constant {@link #CLIMB_SPEED}.
     *
     * @param direction the climbing direction (positive for up, negative for down)
     * @throws NullPointerException if {@code mario} is null
     */
    @Override
    public void climb(final Mario mario, final int direction) {
        Objects.requireNonNull(mario);
        mario.setPosition(new Position(mario.getPosition().x(),
                                        mario.getPosition().y() + direction * CLIMB_SPEED));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation applies the hammer power.
     * Mario cannot use a hammer if he doesn't possess one.
     *
     * @throws NullPointerException if {@code mario} is null
     */
    @Override
    public void useHammer(final Mario mario) {
        Objects.requireNonNull(mario);
        // Intentionally empty - only WithHammerState implements this
    }
}
