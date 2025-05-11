package it.unibo.coffebreak.model.api.entities.platform;

import it.unibo.coffebreak.model.api.entities.Entity;

public interface Platform extends Entity {

    enum PlatformSlope {
        LEFT,
        RIGHT,
        FLAT
    }

    PlatformSlope getSlope();

    boolean isSupporting(Entity entity);
}
