package it.unibo.coffebreak.model.api.entity.donkeykong;

import it.unibo.coffebreak.model.impl.entity.donkeykong.DonkeyKong;

/**
 * A factory interface for creating command objects that encapsulate
 * Donkey Kong's actions. This follows the Command Pattern to decouple
 * action creation from execution.
 *
 * <p>Implementations should provide thread-safe command instances
 * that can be executed at a later time.
 *
 * @see Command
 * @see DonkeyKong
 */
public interface CommandFactory {

    /**
     * Creates a command that encapsulates the barrel throwing action.
     *
     * @param donkeyKong the DonkeyKong instance that will execute the action (not null)
     * @return a new {@link Command} instance that invokes {@link DonkeyKong#throwBarrel()}
     * @throws IllegalArgumentException if donkeyKong parameter is null
     */
    Command createThrowCommand(DonkeyKong donkeyKong);

    /**
     * Creates a command that encapsulates the anger increase action.
     *
     * @param donkeyKong the DonkeyKong instance that will execute the action (not null)
     * @return a new {@link Command} instance that invokes {@link DonkeyKong#increaseAnger()}
     * @throws IllegalArgumentException if donkeyKong parameter is null
     */
    Command createAngerCommand(DonkeyKong donkeyKong);
}
