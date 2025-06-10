package it.unibo.coffebreak.impl.view.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.view.render.entities.enemy.barrel.BarrelRender;
import it.unibo.coffebreak.impl.view.render.entities.mario.PlayerRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.ladder.LadderRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.platform.PlatformRender;

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
public final class GameRenderManager implements RenderManager {

    private final Map<Class<? extends Entity>, EntityRender> entityRenderers = new HashMap<>();
    private final Loader resources;
    private final int screenWidth;
    private final int screenHeight;
    // TODO: andrebbero presi dalla view e poi dovrebbero variare per la questione
    // del resize ecc...

    /**
     * Constructs a new GameRenderManagerImpl with the specified initial dimensions.
     * 
     * @param resources    Resource loader per le immagini/font
     * @param screenWidth  Larghezza iniziale dello schermo
     * @param screenHeight Altezza iniziale dello schermo
     */
    public GameRenderManager(final Loader resources, final int screenWidth, final int screenHeight) {
        this.resources = Objects.requireNonNull(resources);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        registerAllEntityRenders();
    }

    /**
     * Registers all default entity renders with their corresponding entity types.
     */
    private void registerAllEntityRenders() {
        registerRenderer(Platform.class, new PlatformRender(resources));
        registerRenderer(Mario.class, new PlayerRender(resources));
        registerRenderer(Barrel.class, new BarrelRender(resources));
        registerRenderer(Ladder.class, new LadderRender(resources));
    }

    /**
     * Registers a new renderer for a specific entity type.
     * 
     * @param entityType the entity class
     * @param renderer   the renderer implementation
     */
    private void registerRenderer(final Class<? extends Entity> entityType, final EntityRender renderer) {
        entityRenderers.put(Objects.requireNonNull(entityType, "Entity type cannot be null"),
                Objects.requireNonNull(renderer, "Renderer cannot be null"));
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
     * @param g         the {@link Graphics2D} context to render onto
     * @param entities
     * @param deltaTime
     * @throws NullPointerException if the graphics context or the list is null
     */
    @Override
    public void render(final Graphics2D g, final List<Entity> entities, final float deltaTime) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        Objects.requireNonNull(entities, "Entities list cannot be null");

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        entities.forEach(entity -> {
            entityRenderers.entrySet().stream()
                    .filter(entry -> entry.getKey().isInstance(entity))
                    .findFirst()
                    .ifPresent(entry -> entry.getValue().draw(g, entity, deltaTime));
        });
    }
}
