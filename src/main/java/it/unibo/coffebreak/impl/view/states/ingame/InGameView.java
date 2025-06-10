package it.unibo.coffebreak.impl.view.states.ingame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.impl.view.renders.GameRenderManager;
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

    //TODO: si dovrebbero vedere anche le vite e il punteggio e maybe un timer(?)

    private static final int DEFAULT_WIDTH = 800; //TODO: cost temporanea perchè no me gusta
    private static final int DEFAULT_HEIGHT = 628;
    private final RenderManager renderManager;
    private final List<Entity> entities;

    /**
     * Constructs an InGameView with the specified controller.
     * Initializes the render manager with a default resolution of 800x600 pixels.
     * 
     * @param controller the game controller that manages the game logic and entities
     * @throws IllegalArgumentException if the controller is null
     */
    public InGameView(final Controller controller) {
        super(controller);
        this.entities = new ArrayList<>(controller.getEntities());
        this.renderManager = new GameRenderManager(getResource(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        final double targetRatio = 4.0 / 3.0;
        int drawWidth = width;
        int drawHeight = (int) (width / targetRatio);

        if (drawHeight > height) {
            drawHeight = height;
            drawWidth = (int) (height * targetRatio);
        }

        final int x = (width - drawWidth) / 2;
        final int y = (height - drawHeight) / 2;

        final Graphics2D g2d = (Graphics2D) g.create();

        try {
            g2d.setClip(x, y, drawWidth, drawHeight);
            g2d.translate(x, y);
            this.renderManager.render(g2d, entities, deltaTime);
        } finally {
            g2d.dispose();
        }
    }
}
