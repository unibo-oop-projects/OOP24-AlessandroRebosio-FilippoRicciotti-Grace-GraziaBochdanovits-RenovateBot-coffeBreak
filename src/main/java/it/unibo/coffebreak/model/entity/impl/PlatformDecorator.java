package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Platform;

/**
 * Abstract decorator class for a Platform.
 * This class is used to add additional functionality to a Platform object.
 */
public abstract class PlatformDecorator implements Platform {
    
    private Platform basePlatform;

    /**
     * Constructs a PlatformDecorator with the specified base platform.
     *
     * @param basePlatform the base platform to be decorated
     */
    public PlatformDecorator(final Platform basePlatform) {
        this.basePlatform = basePlatform;
    }

    /**
     * Determines if the platform can break.
     * Delegates the call to the base platform.
     *
     * @return true if the base platform can break, false otherwise
     */
    @Override
    public boolean canBreak() {
        return basePlatform.canBreak();
    }

    /**
     * Gets the friction level of the platform.
     * Delegates the call to the base platform.
     *
     * @return the friction level of the base platform
     */
    @Override
    public float getFriction() {
        return basePlatform.getFriction();
    }
}
