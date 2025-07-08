package it.unibo.coffebreak.impl.model.level.maps.state.map4;

import java.util.List;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.level.maps.state.AbstractMapState;

/**
 * Map state for the fourth level of the game.
 * Loads the map from "maps/map4.txt" and determines advancement
 * based on whether all breakable platforms are broken.
 * 
 * @author Filippo Riciotti
 */
public class GameMapFour extends AbstractMapState {

    /**
     * Constructs the map state for the fourth level.
     * Loads the map data from the resource file "maps/map4.txt"
     * by passing the index 4 to the superclass constructor.
     * 
     * @param loader the loader used to load map resources
     */
    public GameMapFour(final Loader loader) {
        super(4, loader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldAdvance(final List<Entity> entities) {
        return entities.stream()
                .filter(BreakablePlatform.class::isInstance)
                .map(BreakablePlatform.class::cast)
                .noneMatch(bp -> !bp.isBroken());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canDonkeyThrowBarrel() {
        return false;
    }
}
