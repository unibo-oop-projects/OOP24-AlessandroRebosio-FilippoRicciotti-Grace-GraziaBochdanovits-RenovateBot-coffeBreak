package it.unibo.coffebreak.api.common;

/**
 * Represents a generic state in a state machine or state pattern.
 * <p>
 * Implementations can define behavior for entering, exiting, and updating the
 * state. The type parameter {@code T} represents the context or owner of the
 * state.
 * </p>
 *
 * @param <T> the type of the context or owner associated with this state
 * @author Alessandro Rebosio
 */
public interface State<T> {

    /**
     * Called when entering this state.
     *
     * @param t the context or owner entering this state
     */
    void onEnter(T t);

    /**
     * Called when exiting this state.
     *
     * @param t the context or owner exiting this state
     */
    void onExit(T t);

    /**
     * Updates the state logic.
     *
     * @param t         the context or owner being updated
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    void update(T t, float deltaTime);

}
