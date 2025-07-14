package it.unibo.coffebreak.api.controller.mapper;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import it.unibo.coffebreak.api.controller.action.Action;

/**
 * Manages input key mappings using functional programming principles.
 * Provides immutable key-to-action mappings and Optional-based lookups.
 * 
 * @author Alessandro Rebosio
 */
public interface KeyActionMapper {

    /**
     * Returns the immutable key mappings for this input manager.
     * 
     * @return the key-to-action mappings
     */
    Map<Integer, Action> getKeyMappings();

    /**
     * Retrieves the action associated with the given key code.
     * 
     * @param keyCode the key code to look up
     * @return an Optional containing the associated Action, or empty if not found
     */
    default Optional<Action> getAction(final int keyCode) {
        return Optional.ofNullable(this.getKeyMappings().get(keyCode));
    }

    /**
     * Checks if the given key code is mapped to an action.
     * 
     * @param keyCode the key code to check
     * @return true if the key is mapped, false otherwise
     */
    default boolean isMapped(final int keyCode) {
        return this.getKeyMappings().containsKey(keyCode);
    }

    /**
     * Processes all mapped actions from a stream of key codes.
     * <p>
     * This method is particularly useful for:
     * <ul>
     * <li>Batch processing of multiple simultaneous key presses</li>
     * <li>Combo detection and processing (e.g., fighting game combinations)</li>
     * <li>Macro execution from stored key sequences</li>
     * </ul>
     * </p>
     * 
     * @param keyCodes stream of key codes to process
     * @return stream of corresponding actions, filtering out unmapped keys
     */
    default Stream<Action> processKeys(final Stream<Integer> keyCodes) {
        return keyCodes
                .map(this::getAction)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
