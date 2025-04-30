package it.unibo.coffebreak.model.impl.entity.platform;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entity.platform.Platform;

/**
 * Abstract base class for platform decorators in the game.
 * Implements the Decorator pattern to dynamically add responsibilities to {@link Platform} objects
 * without affecting other instances of the same class. 
 * Subclasses should override specific methods to modify platform behavior while delegating
 * other calls to the wrapped platform via {@code basePlatform}.
 */
public abstract class PlatformDecorator extends AbstractPlatform {

    private final Platform basePlatform;

     /**
     * Constructs a new platform decorator wrapping the specified base platform.
     * <p>
     * Initializes the decorator with the base platform's position and dimensions.
     * The actual behavior will be delegated to the base platform unless overridden.
     * </p>
     *
     * @param basePlatform the platform instance to be decorated (cannot be {@code null})
     * @throws IllegalArgumentException if basePlatform is {@code null}
     */
    public PlatformDecorator(final Platform basePlatform) {
        super(((AbstractPlatform) Objects.requireNonNull(basePlatform)).getPosition(),
                ((AbstractPlatform) basePlatform).getDimension());
        this.basePlatform = basePlatform;
    }

    /**
     * Determines if the decorated platform can break.
     * Delegates the call to the wrapped platform instance.
     *
     * @return {@code true} if the base platform can break, {@code false} otherwise
     * @see Platform#canBreak()
     */
    @Override
    public boolean canBreak() {
        return this.basePlatform.canBreak();
    }

    /**
     * Gets the friction level of the decorated platform.
     * Delegates the call to the wrapped platform instance.
     *
     * @return the friction level of the base platform
     * @see Platform#getFriction()
     */
    @Override
    public float getFriction() {
        return this.basePlatform.getFriction();
    }

}
