package it.unibo.coffebreak.model.impl.physics;

import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.physics.Collision;

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
public class GameCollision implements Collision {

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkCollision(final Model model) {
        Objects.requireNonNull(model, "Model cannot be null");

        this.checkMarioCollisions(model);
        this.checkEntityCollisions(model.getEntities());
    }

    /**
     * Checks collisions between the player character and other entities such as
     * enemies, platforms, ladders, and collectibles.
     * 
     * @param model the game model containing the player and other entities
     */
    private void checkMarioCollisions(final Model model) {
        final Character mario = model.getPlayer();

        for (final Entity entity : model.getEntities()) {
            if (!entity.equals(mario) && mario.intersect(entity)) {
                entity.onCollision(mario);
            }
        }
    }

    /**
     * Checks collisions between all pairs of entities and triggers their respective
     * {@code onCollision} methods if they intersect.
     * 
     * @param entities the list of all entities in the game
     */
    private void checkEntityCollisions(final List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            final Entity a = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                final Entity b = entities.get(j);
                if (a.intersect(b)) {
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }
    }
}
