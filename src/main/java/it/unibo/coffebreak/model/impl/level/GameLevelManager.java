package it.unibo.coffebreak.model.impl.level;

import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.level.cleaner.Cleaner;
import it.unibo.coffebreak.model.impl.level.cleaner.EntityCleaner;

public class GameLevelManager implements LevelManager {

    private final Cleaner cleanup;

    public GameLevelManager() {
        this.cleanup = new EntityCleaner();
    }

    @Override
    public void loadEntities() {

    }

    @Override
    public void resetEntities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetEntities'");
    }

    @Override
    public void nextMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nextMap'");
    }

    @Override
    public void cleanEntities() {
        this.cleanup.clean(null);
    }

    @Override
    public void nextLevel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nextLevel'");
    }

}
