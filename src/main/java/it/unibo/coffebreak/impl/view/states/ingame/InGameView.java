package it.unibo.coffebreak.impl.view.states.ingame;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.view.renders.RenderManager;
import it.unibo.coffebreak.impl.view.renders.GameRenderManagerImpl;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

/**
 * Represents the in-game view state of the game.
 * This class handles the rendering of game entities during gameplay
 * using a dedicated RenderManager.
 * 
 * <p>It extends {@link AbstractViewState} to inherit common view state functionality
 * and implements the game-specific rendering logic.</p>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class InGameView extends AbstractViewState {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private final RenderManager renderManager;

    /**
     * Constructs an InGameView with the specified controller.
     * Initializes the render manager with a default resolution of 800x600 pixels.
     * 
     * @param controller the game controller that manages the game logic and entities
     * @throws IllegalArgumentException if the controller is null
     */
    public InGameView(final Controller controller) {
        super(controller);
        this.renderManager = new GameRenderManagerImpl(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Renders the current game state by updating and drawing all entities.
     * 
     * @param g the Graphics2D object used for rendering
     * @param width the available width for rendering
     * @param height the available height for rendering
     * @throws IllegalArgumentException if the Graphics2D object is null
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height) {
        this.renderManager.updateEntities(super.getController().getEntities());
        this.renderManager.render(g, width, height);
    }
}
