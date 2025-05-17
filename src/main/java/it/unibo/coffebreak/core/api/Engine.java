package it.unibo.coffebreak.core.api;

/**
 * Core game engine interface that drives the game loop and coordinates
 * updates between different game systems.
 * 
 * @author Alessandro Rebosio
 */
public interface Engine {
    /**
     * Starts and runs the main game loop.
     * The implementation should handle game timing, updates, and rendering.
     */
    void run();
}
