package it.unibo.coffebreak.impl.model.level.maps.state.map1;

import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.impl.model.level.maps.state.AbstractMapState;

/**
 * Map state for the first level of the game.
 * Loads the map from "maps/map1.txt" and determines advancement
 * based on whether the princess has been rescued.
 * 
 * @author Filippo Ricciotti
 */
public class GameMapOne extends AbstractMapState {

    /**
     * Constructs the map state for the fourth level.
     * Loads the map data from the resource file "maps/map4.txt"
     * by passing the index 1 to the superclass constructor.
     */
    public GameMapOne() {
        super(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldAdvance(final List<Entity> entities) {
        return entities.stream()
                .filter(Princess.class::isInstance)
                .map(Princess.class::cast)
                .anyMatch(Princess::isRescued);
    }

}
