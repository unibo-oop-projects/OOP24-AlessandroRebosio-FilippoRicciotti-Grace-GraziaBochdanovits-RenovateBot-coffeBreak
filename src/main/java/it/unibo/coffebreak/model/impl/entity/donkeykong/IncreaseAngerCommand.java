package it.unibo.coffebreak.model.impl.entity.donkeykong;

import it.unibo.coffebreak.model.api.entity.donkeykong.Command;
import it.unibo.coffebreak.model.api.entity.donkeykong.Villain;

import java.util.Objects;

/**
 * A concrete {@link Command} implementation that encapsulates the action
 * of increasing a {@link Villain}'s anger level.
 * 
 * <p>This command follows the Command Pattern to decouple the request for
 * an anger increase from its execution.
 * 
 * <p>This implementation is:
 * <ul>
 *   <li>Immutable: The villain reference cannot be changed after creation</li>
 *   <li>Thread-safe: No internal state is modified after construction</li>
 *   <li>Null-safe: Validates constructor parameter</li>
 * </ul>
 */
public final class IncreaseAngerCommand implements Command {

    private final Villain villain;

    /**
     * Creates a new command that will increase the specified villain's anger level.
     *
     * @param villain the villain whose anger should be increased (not null)
     * @throws IllegalArgumentException if villain parameter is null
     */
    public IncreaseAngerCommand(final Villain villain) {
        this.villain = Objects.requireNonNull(villain, "Villain parameter cannot be null");
    }

    /**
     * Executes the anger increase action by delegating to the contained {@link Villain}.
     * 
     * <p>The actual behavior depends on the villain's implementation of
     * {@link Villain#increaseAnger()}.
     */
    @Override
    public void execute() {
        villain.increaseAnger();
    }

    /**
     * Gets the villain associated with this command.
     * 
     * @return the villain instance (never null)
     */
    public Villain getVillain() {
        return villain;
    }
}
