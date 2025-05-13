package it.unibo.coffebreak.core.api;

/**
 * Represents the core game engine that drives the game loop and coordinates
 * updates between different game systems.
 * 
 * @author Alessandro Rebosio
 */
public interface Engine {

    /**
     * Starts and runs the main game loop.
     * 
     * @throws IllegalStateException if the engine cannot start or run properly
     */
    void run();
}
