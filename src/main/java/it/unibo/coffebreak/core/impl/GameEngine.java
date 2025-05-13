package it.unibo.coffebreak.core.impl;


import it.unibo.coffebreak.core.api.Engine;

public class GameEngine implements Engine {

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

    protected void waitForNextFrame(final long cycleTime) {
        final long dt = System.currentTimeMillis() - cycleTime;
        if (dt < period) {
            try {
                Thread.sleep(period - dt);
            } catch (final Exception ex) {
            }
        }
    }

}
