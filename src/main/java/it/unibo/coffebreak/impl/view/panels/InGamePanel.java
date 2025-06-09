package it.unibo.coffebreak.impl.view.panels;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Objects;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.panels.GameStatePanel;
import it.unibo.coffebreak.api.view.renders.RenderManager;
import it.unibo.coffebreak.impl.view.renders.GameRenderManagerImpl;

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
    private final Controller controller;

    /**
     * Constructs a new InGameScreen with the specified render manager and resource
     * loader.
     * 
     * @param controller   the {@link Controller} used to get the entities to render
     * @param screenWidth  the width of the panel
     * @param screenHeight the height of the panel
     */
    public InGamePanel(final Controller controller, final int screenWidth, final int screenHeight) {
        this.renderManager = new GameRenderManagerImpl(screenWidth, screenHeight);
        this.controller = Objects.requireNonNull(controller, "Model cannot be null");
        this.renderManager.updateEntities(this.controller.getEntities());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.renderManager.updateEntities(Collections.unmodifiableList(controller.getEntities()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final int width, final int height) {
        this.renderManager.render(g, width, height);
    }
}
