package it.unibo.coffebreak.model.impl.physics;

import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.entities.ladder.Ladder;
import it.unibo.coffebreak.model.api.entities.platform.Platform;
import it.unibo.coffebreak.model.api.physics.Collision;

public class GameCollision implements Collision {

    @Override
    public void checkCollision(final Model model) {
        Objects.requireNonNull(model, "Model cannot be null");

        this.checkMarioCollisions(model);
        this.checkEntityCollisions(model.getEntities());
    }

    private void checkMarioCollisions(final Model model) {
        final Character mario = model.getPlayer();

        for (final Entity entity : model.getEntities()) {
            if (entity != mario && mario.intersect(entity)) {
                if (entity instanceof final Collectible item) {
                    model.getScoreManager().earnPoints(item.getPointsValue());
                    item.collect();
                } else if (entity instanceof final Enemy enemy) {
                    this.handleEnemyCollision(mario, enemy);
                } else if (entity instanceof final Platform platform) {
                    this.handlePlatformCollision(mario, platform);
                } else if (entity instanceof final Ladder ladder) {
                    this.handleLadderCollision(mario, ladder);
                }
            }
        }
    }

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

    private void handleEnemyCollision(final Character mario, final Enemy enemy) {
    }

    private void handlePlatformCollision(final Character mario, final Platform platform) {
    }

    private void handleLadderCollision(final Character mario, final Ladder ladder) {
    }

}
