package it.unibo.coffebreak.model.impl.physics.collision;

import java.util.Objects;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.physics.collision.Collision;

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

        model.getPlayer().ifPresent(player -> model.getEntities()
                .stream().filter(e -> !e.equals(player) && player.collidesWith(e))
                .forEach(player::onCollision));

        model.getEntities().forEach(a -> model.getEntities().stream()
                .filter(b -> !a.equals(b))
                .filter(a::collidesWith)
                .forEach(b -> {
                    a.onCollision(b);
                    b.onCollision(a);
                }));
    }

}
