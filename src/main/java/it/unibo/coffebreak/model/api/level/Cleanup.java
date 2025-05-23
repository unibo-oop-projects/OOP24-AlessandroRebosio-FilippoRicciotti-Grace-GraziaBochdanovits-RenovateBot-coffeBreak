package it.unibo.coffebreak.model.api.level;

import java.util.List;

import javax.swing.text.html.parser.Entity;

public interface Cleanup {
    void cleanUpEntity(List<Entity> entities);
}
