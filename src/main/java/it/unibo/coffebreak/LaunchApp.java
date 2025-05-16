package it.unibo.coffebreak;

import it.unibo.coffebreak.core.impl.GameEngine;

/**
 * Entry point of the app, it creates a controller and launches the GUI
 * so that the game can start.
 */
final class LaunchApp {

    private LaunchApp() {
    }

    /**
     * Starts the application.
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
        System.out.println("Starting Game..."); // NOPMD suppressed as it is a false positive
        new GameEngine().run();
    }
}
