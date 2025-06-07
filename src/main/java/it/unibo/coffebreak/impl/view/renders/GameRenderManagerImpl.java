package it.unibo.coffebreak.impl.view.renders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.renders.EntityRender;
import it.unibo.coffebreak.api.view.renders.RenderManager;
import it.unibo.coffebreak.api.view.renders.ScalableRender;
import it.unibo.coffebreak.api.view.renders.StaticRender;

/**
 * Implementation of {@link RenderManager} that manages the rendering process
 * for both static and dynamic game entities. This class coordinates the drawing of all
 * game elements in the proper order and delegates the actual rendering to
 * specialized renderers for each entity type.
 * 
 * <p>
 * The rendering process follows this sequence:
 * <ol>
 * <li>Clear the screen</li>
 * <li>Render static elements (background, level, etc.)</li>
 * <li>Render dynamic entities in the order they were added</li>
 * </ol>
 * </p>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public final class GameRenderManagerImpl implements RenderManager {

    private final List<StaticRender> staticRenders = new ArrayList<>();
    private final Map<Class<? extends Entity>, EntityRender> entityRenderers = new HashMap<>();
    private final List<Entity> entities = new ArrayList<>();

    private int lastWidth = -1;
    private int lastHeight = -1;

    /**
     * <p>
     * Renders all game elements in the following order:
     * <ol>
     * <li>Clears the screen with a black background</li>
     * <li>Renders all registered static elements</li>
     * <li>Renders all dynamic entities using their respective renderers</li>
     * </ol>
     * </p>
     * 
     * <p>
     * This method also handles resize notifications when the dimensions change.
     * </p>
     * 
     * @param g      the {@link Graphics2D} context to render onto
     * @param width  the current width of the rendering area
     * @param height the current height of the rendering area
     * @throws NullPointerException if the graphics context is null
     */
    @Override
    public void render(final Graphics2D g, final int width, final int height) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        if (lastWidth < 0 || lastHeight < 0 || width != lastWidth || height != lastHeight) {
            notifyResize(width, height);
            lastWidth = width;
            lastHeight = height;
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        staticRenders.forEach(render -> render.render(g));

        entities.forEach(entity -> {
            entityRenderers.entrySet().stream()
                .filter(entry -> entry.getValue().canRender(entity))
                .findFirst()
                .ifPresent(entry -> entry.getValue().render(g, entity));
        });
    }

    /**
     * Notifies all registered renders about a resize event.
     * Only renders implementing {@link ScalableRender} will be notified.
     * 
     * @param newW the new width
     * @param newH the new height
     */
    private void notifyResize(final int newW, final int newH) {

        staticRenders.stream().filter(ScalableRender.class::isInstance)
                .map(ScalableRender.class::cast)
                .forEach(render -> render.onResize(newW, newH));

        entityRenderers.values().stream().filter(ScalableRender.class::isInstance)
                .map(ScalableRender.class::cast)
                .forEach(render -> render.onResize(newW, newH));
    }

    /**
     * {@inheritDoc}
     * 
     * @param render the static render to add
     * @throws NullPointerException if the render is null
     */
    @Override
    public void addStaticRenderer(final StaticRender render) {
        staticRenders.add(Objects.requireNonNull(render, "Static render cannot be null"));
    }

    /**
     * {@inheritDoc}
     * 
     * @param type         the type of entity the renderer can handle
     * @param renderer    the renderer to add
     * @throws NullPointerException if either parameter is null
     */
    @Override
    public void addEntityRenderer(final Class<? extends Entity> type, final EntityRender renderer) {
        entityRenderers.put(Objects.requireNonNull(type), Objects.requireNonNull(renderer));
    }

    /**
     * {@inheritDoc}
     * 
     * @param entities the list of entities to render
     * @throws NullPointerException if the entities list is null
     */
    @Override
    public void updateEntities(final List<Entity> entities) {
        this.entities.clear();
        this.entities.addAll(Objects.requireNonNull(entities, "Entities list cannot be null"));
    }
}
