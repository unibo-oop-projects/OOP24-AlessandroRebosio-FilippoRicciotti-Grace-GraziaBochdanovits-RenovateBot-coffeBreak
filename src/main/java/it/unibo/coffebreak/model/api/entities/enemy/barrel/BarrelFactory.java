package it.unibo.coffebreak.model.api.entities.enemy.barrel;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.impl.common.Position2D;

/**
 * Factory interface for creating Barrel instances.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface BarrelFactory {

    /**
     * Creates a new barrel instance.
     * 
     * @param position the initial position
     * @param initialDirection the direction on the barrel
     * @return a new Barrel instance
     */
    Barrel createBarrel(Position2D position, Command initialDirection);
}

