package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Platform;

/**
 * Represents a fragile platform in the game.
 * This class extends the PlatformDecorator to provide a platform that can break.
 */
public class FragilePlatform extends PlatformDecorator {

    /**
     * Constructs a FragilePlatform with the specified base platform.
     *
     * @param basePlatform the base platform to be decorated
     */
    public FragilePlatform(final Platform basePlatform) {
        super(basePlatform);
    }

    /**
     * Determines if the platform can break.
     * This implementation always returns true, indicating that the platform is fragile.
     *
     * @return true, indicating that the platform can break
     */
    @Override
    public boolean canBreak() {
        return true; 
    }

}
