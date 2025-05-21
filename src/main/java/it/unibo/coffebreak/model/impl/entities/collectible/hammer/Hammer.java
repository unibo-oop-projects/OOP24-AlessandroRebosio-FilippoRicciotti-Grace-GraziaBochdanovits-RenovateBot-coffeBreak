package it.unibo.coffebreak.model.impl.entities.collectible.hammer;

import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.impl.entities.collectible.AbstractCollectible;
import it.unibo.coffebreak.model.impl.entities.mario.states.withhammer.WithHammerState;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;

/**
 * Represents a hammer collectible item in the game. When collected by a player
 * character, it provides both points to the player's score and potentially
 * modifies the player's state.
 * Extends {@link AbstractCollectible} to inherit basic collectible properties
 * and behavior.
 * 
 * @author Alessandro Rebosio
 */
public class Hammer extends AbstractCollectible {

    /**
     * Constructs a new Hammer with the specified position, dimensions, and point
     * value.
     *
     * @param position  the 2D position of the hammer in the game world
     * @param dimension the 2D dimensions (size) of the hammer
     * @param value     the point value this hammer is worth when collected
     */
    public Hammer(final Position2D position, final BoundingBox2D dimension, final int value) {
        super(position, dimension, value);
    }

    /**
     * Applies the effect of collecting this hammer to the player character.
     * When collected, the hammer adds its value to the player's score and
     * may modify the player's state (e.g., granting special abilities).
     * 
     * @param character the character that collected this hammer
     */
    @Override
    protected void applyEffect(final Character character) {
        super.applyEffect(character);
        character.changeState(WithHammerState::new);
    }
}
