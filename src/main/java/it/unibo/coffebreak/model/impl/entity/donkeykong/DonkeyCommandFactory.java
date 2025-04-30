package it.unibo.coffebreak.model.impl.entity.donkeykong;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entity.donkeykong.Command;
import it.unibo.coffebreak.model.api.entity.donkeykong.CommandFactory;

/**
 * Default implementation of {@link CommandFactory} for Donkey Kong actions.
 * Creates command objects using method references for optimal performance.
 *
 * <p>This implementation is:
 * <ul>
 *   <li>Thread-safe: No shared state between instances</li>
 *   <li>Lightweight: Commands are simple method references</li>
 *   <li>Null-safe: Validates input parameters</li>
 * </ul>
 *
 * @see CommandFactory
 */
public final class DonkeyCommandFactory implements CommandFactory {

    /**
     * Creates a command that will invoke {@link DonkeyKong#throwBarrel()}.
     *
     * @param donkeyKong the DonkeyKong instance to delegate to (not null)
     * @return a command encapsulating the barrel throw action
     * @throws IllegalArgumentException if donkeyKong is null
     */
    @Override
    public Command createThrowCommand(final DonkeyKong donkeyKong) {
        Objects.requireNonNull(donkeyKong, "DonkeyKong instance cannot be null");
        return donkeyKong::throwBarrel;
    }

    /**
     * Creates a command that will invoke {@link DonkeyKong#increaseAnger()}.
     *
     * @param donkeyKong the DonkeyKong instance to delegate to (not null)
     * @return a command encapsulating the anger increase action
     * @throws IllegalArgumentException if donkeyKong is null
     */
    @Override
    public Command createAngerCommand(final DonkeyKong donkeyKong) {
        Objects.requireNonNull(donkeyKong, "DonkeyKong instance cannot be null");
        return donkeyKong::increaseAnger;
    }
}
