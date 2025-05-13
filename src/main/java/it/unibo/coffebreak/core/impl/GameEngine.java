package it.unibo.coffebreak.core.impl;

import it.unibo.coffebreak.core.api.Engine;

/**
 * Implementation of {@link Engine} that manages the game loop with a fixed
 * timestep.
 * This engine maintains a consistent frame rate (approximately 60 FPS) by using
 * a fixed period between frames and sleep-based timing control.
 * 
 * @author Alessandro Rebosio
 */
public class GameEngine implements Engine {

    /**
     * The target frame period in milliseconds (16ms ≈ 60 FPS).
     */
    private final long period = 16;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        // TODO: long previusCycle = System.currentTimeMillis();
        while (true) {
            final long currentCycle = System.currentTimeMillis();
            // TODO: final long elapsed = currentCycle - previusCycle;

            this.waitForNextFrame(currentCycle);
            // TODO: previusCycle = currentCycle;
        }
        // TODO: renderGameOver()
    }

    /**
     * Waits the necessary time to maintain consistent frame timing.
     * If the current frame completed faster than the target period,
     * this method sleeps the remaining time.
     *
     * @param cycleTime the timestamp when the current frame started (in
     *                  milliseconds)
     * @implNote This implementation silently swallows thread interruption
     *           exceptions,
     *           which should typically be handled more gracefully.
     */
    protected void waitForNextFrame(final long cycleTime) {
        final long dt = System.currentTimeMillis() - cycleTime;
        if (dt < period) {
            try {
                Thread.sleep(period - dt);
            } catch (final Exception ex) {
                // TODO: Handle interruption properly
            }
        }
    }
}