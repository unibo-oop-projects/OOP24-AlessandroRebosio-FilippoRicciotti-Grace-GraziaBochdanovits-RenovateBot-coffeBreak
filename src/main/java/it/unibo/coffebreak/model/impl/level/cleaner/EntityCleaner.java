package it.unibo.coffebreak.model.impl.level.cleaner;

import java.util.List;
import java.util.Objects;

import javax.swing.text.html.parser.Entity;

import it.unibo.coffebreak.model.api.level.cleaner.Cleaner;

public class EntityCleaner implements Cleaner {

    @Override
    public void clean(List<Entity> entities) {
        Objects.requireNonNull(entities, "The entities cannot be null");
        
    }

}
