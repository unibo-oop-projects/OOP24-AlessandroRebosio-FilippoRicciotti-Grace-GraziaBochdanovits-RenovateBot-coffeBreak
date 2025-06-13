package it.unibo.coffebreak.impl.core;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.core.Engine;
import it.unibo.coffebreak.impl.controller.GameController;
import it.unibo.coffebreak.impl.view.GameView;

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

    private final Controller controller = new GameController();
    private final GameView view = new GameView(this.controller);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        long previousTime = System.currentTimeMillis();

        while (controller.isGameActive()) {
            final long currentTime = System.currentTimeMillis();
            final float deltaTime = (currentTime - previousTime) / 1000.0f;

            this.controller.processInput();
            this.controller.updateModel(deltaTime);
            this.view.update(deltaTime);

            this.sleepUntilNextFrame(currentTime);
            previousTime = currentTime;
        }

        view.close();
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
    protected void sleepUntilNextFrame(final long cycleTime) {
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
