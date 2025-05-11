package it.unibo.coffebreak.model.api.entities.collectible;

import it.unibo.coffebreak.model.api.entities.Entity;

public interface Collectible extends Entity {

    void collect();

    boolean isCollected();

}
