package it.unibo.coffebreak.impl.view.renders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.view.renders.EntityRender;
import it.unibo.coffebreak.api.view.renders.RenderManager;
import it.unibo.coffebreak.api.view.renders.ScalableRender;
import it.unibo.coffebreak.api.view.renders.StaticRender;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.view.renders.entityrenders.barrel.BarrelRender;
import it.unibo.coffebreak.impl.view.renders.entityrenders.ladder.LadderRender;
import it.unibo.coffebreak.impl.view.renders.entityrenders.mario.PlayerRender;
import it.unibo.coffebreak.impl.view.renders.entityrenders.platform.PlatformRender;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

/**
 * Implementation of {@link RenderManager} that manages the rendering process
 * for both static and dynamic game entities. This class coordinates the drawing
 * of all
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
public final class GameRenderManagerImpl implements RenderManager { // TODO: leave it empty, with only render method 

    private final List<StaticRender> staticRenders = new ArrayList<>(); // TODO: capire se hanno senso gli static renders
    private final Map<Class<? extends Entity>, EntityRender> entityRenderers = new HashMap<>();
    private List<Entity> entities = Collections.emptyList();

    private int lastWidth = -1;
    private int lastHeight = -1;

    /**
     * Constructs a new GameRenderManagerImpl with the specified initial dimensions.
     * 
     * @param screenWidth the initial width of the rendering area
     * @param screenHeight the initial height of the rendering area
     */
    public GameRenderManagerImpl(final int screenWidth, final int screenHeight) {
        final ResourceLoader resources = new ResourceLoader();
        registerAllEntityRenders(resources, screenWidth, screenHeight);
    }

    /**
     * Registers all default entity renders with their corresponding entity types.
     * 
     * @param resources the resource loader to use for render initialization
     * @param width the initial width for the renders
     * @param height the initial height for the renders
     */
    private void registerAllEntityRenders(final ResourceLoader resources, final int width, final int height) {
        entityRenderers.put(Platform.class, new PlatformRender(resources, width, height));
        entityRenderers.put(Mario.class, new PlayerRender(width, height));
        entityRenderers.put(Barrel.class, new BarrelRender(width, height));
        entityRenderers.put(Ladder.class, new LadderRender(width, height));
    }

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
     * @param type     the type of entity the renderer can handle
     * @param renderer the renderer to add
     * @throws NullPointerException if either parameter is null
     */
    @Override
    public void registerEntityRenderer(final Class<? extends Entity> type, final EntityRender renderer) {
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
        this.entities = List.copyOf(Objects.requireNonNull(entities, "Entities list cannot be null"));
    }
}
