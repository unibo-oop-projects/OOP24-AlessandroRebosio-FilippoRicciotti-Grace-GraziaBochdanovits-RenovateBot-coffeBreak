package it.unibo.coffebreak.model.impl.entity.donkeykong;

import it.unibo.coffebreak.model.api.entity.donkeykong.Command;
import it.unibo.coffebreak.model.api.entity.donkeykong.Villain;

import java.util.Objects;

/**
 * Concrete implementation of {@link Command} that encapsulates the action
 * of throwing a barrel by a {@link Villain} entity.
 * 
 * <p>Follows the Command Pattern to decouple the execution of the action
 * from its invocation.
 *
 * <p>Characteristics:
 * <ul>
 *   <li><b>Immutable</b>: The villain reference is final</li>
 *   <li><b>Thread-safe</b>: No internal state modification after construction</li>
 *   <li><b>Null-safe</b>: Validates constructor parameter</li>
 * </ul>
 *
 * @see Command
 * @see Villain#throwBarrel()
 */
public final class ThrowBarrelCommand implements Command {

    private final Villain villain;

    /**
     * Creates a new command to execute the barrel throwing action.
     *
     * @param villain the villain entity that will perform the action (not null)
     * @throws IllegalArgumentException if villain parameter is null
     */
    public ThrowBarrelCommand(final Villain villain) {
        this.villain = Objects.requireNonNull(villain, "Villain parameter cannot be null");
    }

    /**
     * Executes the barrel throwing action by delegating to the contained villain.
     * The actual behavior depends on the villain's implementation of
     * {@link Villain#throwBarrel()}.
     */
    @Override
    public void execute() {
        villain.throwBarrel();
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
