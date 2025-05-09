package it.unibo.coffebreak.model.api.entity.mario;

/**
 * Defines the behavior interface for Mario's various states in the State
 * Pattern implementation.
 * Each concrete state class implements these methods to provide state-specific
 * behavior.
 * <p>
 * This interface follows the State design pattern to encapsulate different
 * behaviors
 * for Mario depending on his current state (e.g., standing, jumping, climbing).
 * 
 * @see Mario
 */
public interface CharacterState {

    void updateState(Character character, float deltaTime);

    void onEnter(Character character);

    void onExit(Character character);
}
