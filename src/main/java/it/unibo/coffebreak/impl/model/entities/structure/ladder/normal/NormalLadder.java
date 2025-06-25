package it.unibo.coffebreak.impl.model.entities.structure.ladder.normal;

import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.AbstractLadder;

/**
 * A concrete implementation of a standard ladder entity within the game.
 * This ladder allows entities such as the player or enemies to move vertically
 * when intersecting with it. Currently, the collision behavior is not defined
 * and should be implemented based on game logic requirements.
 * 
 * This class extends {@link AbstractLadder}.
 * 
 * @author Alessandro Rebosio
 */
public class NormalLadder extends AbstractLadder {

    /**
     * Constructs a new NormalLadder with the specified position and dimensions.
     * 
     * @param position  the top-left position of the ladder in the game world
     * @param dimension the dimension of the ladder in the game world
     */
    public NormalLadder(final Position position, final BoundigBox dimension) {
        super(position, dimension);
    }
}
