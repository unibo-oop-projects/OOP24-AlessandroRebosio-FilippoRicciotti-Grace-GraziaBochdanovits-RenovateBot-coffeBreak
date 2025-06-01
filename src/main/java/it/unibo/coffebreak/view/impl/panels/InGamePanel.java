package it.unibo.coffebreak.view.impl.panels;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.view.api.panels.GameStatePanel;
import it.unibo.coffebreak.view.api.renders.RenderManager;
import it.unibo.coffebreak.view.impl.renders.GameRenderManagerImpl;
import it.unibo.coffebreak.view.impl.renders.LevelRender;
import it.unibo.coffebreak.view.impl.resources.ResourceLoader;

/**
 * Implementation of {@link GameStatePanel} that represents the in-game screen.
 * This class manages the game panel and sets up all the necessary renders
 * for displaying the game state, including the level background and player character.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class InGamePanel implements GameStatePanel {

    private final RenderManager renderManager;
    private final List<Entity> entities;

    /**
     * Constructs a new InGameScreen with the specified render manager and resource loader.
     * 
     * @param resources the {@link ResourceLoader} used to load game resouces
     * @param model the {@link Model} used to get the entities to render
     */
    public InGamePanel(final ResourceLoader resources, final Model model) {
        this.entities = model.getEntities();
        this.renderManager = new GameRenderManagerImpl();
        initializeRenders(Objects.requireNonNull(resources));
    }

    /**
     * Initialized the renders for the entity in the game Model.
     * @param resources the rosource loader
     */
    private void initializeRenders(final ResourceLoader resources) {
        renderManager.addStaticRenderer(new LevelRender(Objects.requireNonNull(resources), 1));
        //TODO: add entity renderers
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.renderManager.updateEntities(Collections.unmodifiableList(entities));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final int width, final int height) {
        this.renderManager.render(g, width, height);
    }
}
