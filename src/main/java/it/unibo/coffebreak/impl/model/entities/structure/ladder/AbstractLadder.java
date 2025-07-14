package it.unibo.coffebreak.impl.model.entities.structure.ladder;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;

/**
 * An abstract base implementation for ladder entities within the game.
 * A ladder allows certain entities (e.g., the player or enemies) to move
 * vertically
 * between platforms. This class provides the basic positional and dimensional
 * properties of a ladder, while requiring subclasses to define specific
 * collision behavior.
 * 
 * This class extends {@link AbstractEntity} and implements the {@link Ladder}
 * interface.
 * 
 * @author Alessandro Rebosio
 */
public abstract class AbstractLadder extends AbstractEntity implements Ladder {

    /**
     * Constructs a new AbstractLadder with the given position and dimensions.
     * 
     * @param position  the top-left position of the ladder in the game world
     * @param dimension the dimension of the ladder in the game world
     */
    public AbstractLadder(final Position position, final BoundigBox dimension) {
        super(position, dimension);
    }

    /**
     * Handles collision logic when another entity interacts with this ladder.
     * Currently, this method has no implemented logic.
     * 
     * @param other the entity that collided with the ladder
     */
    @Override
    public void onCollision(final Entity other) {
        switch (other) {
            case final MainCharacter character -> {
                if (character.isClimbing()) {
                    this.centerCharacterOnLadder(character);
                }
            }
            case final Fire fire -> {
                this.centerCharacterOnLadder(fire);
            }
            default -> {
            }
        }
    }

    /**
     * Centers the character horizontally on this ladder.
     * <p>
     * Calculates the center position of the ladder and adjusts the character's
     * X position to align with it, ensuring smooth climbing behavior.
     * </p>
     * 
     * @param entity the entity to center on the ladder
     */
    private void centerCharacterOnLadder(final Entity entity) {
        final float ladderCenterX = this.getPosition().x() + this.getDimension().width() / 2f;
        final float characterHalfWidth = entity.getDimension().width() / 2f;
        final float centeredX = ladderCenterX - characterHalfWidth;

        final var currentPos = entity.getPosition();
        entity.setPosition(new Position(centeredX, currentPos.y()));
    }
}
