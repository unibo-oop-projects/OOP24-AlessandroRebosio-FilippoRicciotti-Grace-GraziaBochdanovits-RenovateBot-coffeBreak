package it.unibo.coffebreak.core.impl;

import it.unibo.coffebreak.controller.api.Controller;
import it.unibo.coffebreak.controller.impl.GameController;
import it.unibo.coffebreak.core.api.Engine;
import it.unibo.coffebreak.view.impl.GameView;

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
    private static final long PERIOD = 16;

    private volatile boolean running = true;
    private final Controller controller;
    private final GameView view;

    /**
     * Constructs a new {@code GameEngine} instance with its own internal
     * {@link Controller}.
     */
    public GameEngine() {
        this.controller = new GameController();
        this.view = new GameView(this.controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        long previusCycle = System.currentTimeMillis();
        while (this.running) {
            final long currentCycle = System.currentTimeMillis();
            final long deltaTime = currentCycle - previusCycle;

            this.controller.processInput();
            if (this.controller.updateModel(deltaTime)) {
                this.stop();
            }
            this.view.update();

            this.waitForNextFrame(currentCycle);

            previusCycle = currentCycle;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.running = false;
        this.view.close();
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
        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
