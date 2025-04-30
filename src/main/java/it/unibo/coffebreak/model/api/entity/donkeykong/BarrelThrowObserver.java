package it.unibo.coffebreak.model.api.entity.donkeykong;

/**
 * An observer interface for receiving notifications about barrel-related events
 * in the Donkey Kong game context. Implementers of this interface can be
 * notified when a barrel is thrown in the game.
 */
public interface BarrelThrowObserver {

    /**
     * Called when a barrel is thrown in the game. Implementing classes should
     * define specific behavior to react to this event.
     */
    void onBarrelThrown();
}
