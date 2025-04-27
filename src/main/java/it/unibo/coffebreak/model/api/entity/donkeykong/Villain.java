package it.unibo.coffebreak.model.api.entity.donkeykong;

/**
 * Represents a villain character in the game context, capable of
 * performing villainous actions like throwing barrels and increasing its anger level.
 * This interface defines the basic behavior expected from villain entities in the game.
 */
public interface Villain {
    /**
     * Makes the villain throw a barrel. The implementation should handle
     * the creation and launching of a new barrel in the game world.
     * This action is typically triggered by the villain's behavior or game events.
     */
    void throwBarrel();

    /**
     * Increases the villain's anger level. The implementation should define
     * how the anger state affects the villain's behavior and possibly
     * triggers more aggressive actions.
     */
    void increaseAnger();
}
