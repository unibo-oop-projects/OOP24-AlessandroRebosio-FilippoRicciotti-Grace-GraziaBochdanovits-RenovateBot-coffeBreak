package it.unibo.coffebreak.model.api.entities.princess;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Represents a Princess entity in the game world.
 * This interface defines the behavior of a princess that can be rescued.
 * Extends the {@link Entity} interface to inherit common entity functionality.
 * 
 * @author Grazia Bochdanvits de Kavna
 */
public interface Princess extends Entity {

    /**
     * Attempts to rescue the princess.
     * This method should change the state of the princess to "rescued".
     */
    void rescue();

    /**
     * Checks if the princess has been successfully rescued.
     *
     * @return true if the princess has been rescued, false otherwise
     */
    boolean isRescued();
}
