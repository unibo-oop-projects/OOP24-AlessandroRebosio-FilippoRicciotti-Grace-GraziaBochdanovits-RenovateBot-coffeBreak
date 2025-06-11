package it.unibo.coffebreak.impl.model.physics.collision;

import java.util.Objects;

import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.physics.collision.Collision;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;

/**
 * Implementation of the {@link Collision} interface that handles detection and
 * response to collisions between entities in the game model.
 * <p>
 * This class performs two main tasks:
 * <ul>
 * <li>Handles specific collisions between the player character and other
 * entities (platforms, enemies, ladders, collectibles).</li>
 * <li>Handles general collisions between all entities by invoking their
 * {@code onCollision} methods.</li>
 * </ul>
 * </p>
 * 
 * @see Collision
 * @see Entity
 * @see Model
 * 
 * @author Alessandro Rebosio
 */
public final class GameCollision implements Collision {

    private GameCollision() {
    }

    /**
     * Checks for collisions in the game model and triggers appropriate responses.
     * 
     * @param model the game model containing entities to check for collisions
     * @throws IllegalArgumentException if the provided model is null
     */
    public static void checkCollision(final Model model) {
        Objects.requireNonNull(model, "Model cannot be null");
        final Entity player = model.getMainCharacter();

        final Position current = player.getPosition();
        final Dimension bounds = model.getGameBound();
        final Dimension size = player.getDimension();

        final float newX = Math.max(0, Math.min(current.x(), bounds.width() - size.width()));
        final float newY = Math.max(0, Math.min(current.y(), bounds.height() - size.height()));

        player.setPosition(new Position(newX, newY));

        model.getEntities()
                .stream()
                .filter(player::collidesWith)
                .forEach(player::onCollision);

        model.getEntities().stream()
            .filter(a -> !a.equals(player))
            .forEach(a -> model.getEntities().stream()
                .filter(b -> !a.equals(b) && !b.equals(player))
                .filter(a::collidesWith)
                .forEach(b -> {
                    a.onCollision(b);
                    b.onCollision(a);
                }));
    }

}
