package it.unibo.coffebreak.impl.controller.action;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import it.unibo.coffebreak.api.controller.action.ActionQueue;

/**
 * Thread-safe implementation of the {@link ActionQueue} interface.
 * <p>
 * Uses a {@link ConcurrentLinkedDeque} to provide thread-safe operations
 * for queuing and retrieving actions. This implementation is suitable for
 * concurrent environments where multiple threads may be adding or polling
 * actions simultaneously.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameActionQueue implements ActionQueue {

    /**
     * Thread-safe queue for storing actions.
     */
    private final Queue<Action> actions = new ConcurrentLinkedDeque<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(final Action action) {
        return this.actions.add(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action poll() {
        return this.actions.poll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return this.actions.isEmpty();
    }

}
