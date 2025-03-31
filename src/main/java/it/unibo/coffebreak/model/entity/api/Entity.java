package it.unibo.coffebreak.model.entity.api;

import it.unibo.coffebreak.model.entity.impl.Position;
import it.unibo.coffebreak.model.entity.impl.Dimension;

public interface Entity {

    public Position getPosition();

    public Dimension getDimension();

    public abstract void update(long deltaTime);
}
