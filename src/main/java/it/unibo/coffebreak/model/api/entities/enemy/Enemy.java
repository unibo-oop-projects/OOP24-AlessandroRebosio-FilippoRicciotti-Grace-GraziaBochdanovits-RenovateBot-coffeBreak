package it.unibo.coffebreak.model.api.entities.enemy;

import it.unibo.coffebreak.model.api.entities.Entity;

public interface Enemy extends Entity {

    void destroy();

    boolean isDestroyed();

}
