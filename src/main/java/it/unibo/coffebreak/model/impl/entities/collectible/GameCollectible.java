package it.unibo.coffebreak.model.impl.entities.collectible;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.impl.entities.GameEntity;
import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;

public abstract class GameCollectible extends GameEntity implements Collectible {

    private boolean collected;
    private final int value;

    public GameCollectible(Position2D position, Dimension2D dimension, int value) {
        super(position, dimension);
        this.collected = false;
        this.value = value;
    }

    @Override
    public void onCollision(Entity other) {
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void collect(Character player) {
        if (!this.collected) {
            this.collected = true;
            this.applyEffect(player);
        }
    }

    @Override
    public boolean isCollected() {
        return this.collected;
    }

    @Override
    public int getPointsValue() {
        return this.value;
    }

    protected abstract void applyEffect(Character player);
}
