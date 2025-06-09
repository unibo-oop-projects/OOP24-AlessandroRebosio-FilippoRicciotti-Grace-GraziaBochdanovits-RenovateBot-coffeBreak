package it.unibo.coffebreak.impl.view.states;

import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.controller.Controller;
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
    private final Controller controller;

    /**
     * Constructs an AbstractViewState with the specified controller.
     *
     * @param controller the controller associated with this view state; must not be
     *                   null
     * @throws NullPointerException if {@code controller} is null
     */
    public AbstractViewState(final Controller controller) {
        this.controller = Objects.requireNonNull(controller, "The controller cannot be null");
    }

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

    /**
     * Returns the controller associated with this view state.
     *
     * @return the controller instance
     */
    protected final Controller getController() {
        return this.controller;
    }
}
