package it.unibo.coffebreak.view.impl.panels;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Objects;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.structure.Platform;
import it.unibo.coffebreak.view.api.panels.GameStatePanel;
import it.unibo.coffebreak.view.api.renders.RenderManager;
import it.unibo.coffebreak.view.impl.renders.GameRenderManagerImpl;
import it.unibo.coffebreak.view.impl.renders.PlatformRender;
import it.unibo.coffebreak.view.impl.resources.ResourceLoader;

/**
 * Implementation of {@link GameStatePanel} that represents the in-game screen.
 * This class manages the game panel and sets up all the necessary renders
 * for displaying the game state, including the level background and player
 * character.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class InGamePanel implements GameStatePanel {

    private final RenderManager renderManager;
    private final Model model;
    private final ResourceLoader resources;
    private final int screenWidth, screenHeight;

    /**
     * Constructs a new InGameScreen with the specified render manager and resource
     * loader.
     * 
     * @param resources the {@link ResourceLoader} used to load game resouces
     * @param model     the {@link Model} used to get the entities to render
     * @param screenWidth the width of the panel
     * @param screenHeight the height of the panel
     */
    public InGamePanel(final ResourceLoader resources, final Model model, final int screenWidth, final int screenHeight) {
        this.renderManager = new GameRenderManagerImpl();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.resources = Objects.requireNonNull(resources);
        initializeRenders();
    }

    /**
     * Initialized the renders for the entity in the game Model.
     */
    private void initializeRenders() {
        // renderManager.addStaticRenderer(new LevelRender(Objects.requireNonNull(resources), 1));
        model.getEntities().stream()
            .map(Entity::getClass)
            .distinct()
            .forEach(this::registerEntityRenderer);
    }

    private void registerEntityRenderer(final Class<? extends Entity> entityClass) {
        if (Platform.class.isAssignableFrom(entityClass)) {
            renderManager.addEntityRenderer(Platform.class, new PlatformRender(resources, screenWidth, screenHeight));
        }
        //TODO: Add more entity types
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.renderManager.updateEntities(Collections.unmodifiableList(model.getEntities()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final int width, final int height) {
        this.renderManager.render(g, width, height);
    }
}
