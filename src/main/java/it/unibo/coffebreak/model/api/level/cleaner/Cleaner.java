package it.unibo.coffebreak.model.api.level.cleaner;

import java.util.List;

import javax.swing.text.html.parser.Entity;

public interface Cleaner {
    void clean(List<Entity> entities);
}
