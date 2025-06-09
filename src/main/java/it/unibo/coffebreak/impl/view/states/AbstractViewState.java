package it.unibo.coffebreak.impl.view.states;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.view.resources.Resource;
import it.unibo.coffebreak.api.view.states.ViewState;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

/**
 * Abstract implementation of the {@link ViewState} interface.
 * <p>
 * Provides a base class for all view states with access to shared resources,
 * such as fonts, images, or audio files.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public abstract class AbstractViewState implements ViewState {

    private final Resource resourceLoader = new ResourceLoader();

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation does nothing.
     * Subclasses may override to handle setup logic.
     * </p>
     */
    @Override
    public void onEnter() {
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation does nothing.
     * Subclasses may override to handle cleanup logic.
     * </p>
     */
    @Override
    public void onExit() {
    }

    /**
     * {@inheritDoc}
     * Subclasses must implement their own drawing logic.
     */
    @Override
    public abstract void draw(Graphics2D g, int width, int height);

    /**
     * Returns the resource loader used by this view state.
     * <p>
     * Intended for use only by subclasses to load fonts, images, or sounds.
     * </p>
     *
     * @return the {@link Resource} instance for this view
     */
    protected final Resource getResource() {
        return this.resourceLoader;
    }
}
