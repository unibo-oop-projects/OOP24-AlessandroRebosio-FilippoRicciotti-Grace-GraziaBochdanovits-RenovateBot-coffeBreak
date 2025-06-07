package it.unibo.coffebreak.view.impl.renders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;
import it.unibo.coffebreak.view.api.renders.EntityRender;

/**
 * Render implementation for {@link Mario} player entity.
 *
 * @author Grazia Bochdanovits de Kavna
 */
public class PlayerRender extends AbstractScalableRender implements EntityRender {

    //private static final String PLAYER_PATH = "/img/mario_sheet.png";
    private static final int PLAYER_WIDTH = 30;
    private static final int PLAYER_HEIGHT = 30;
    //private final transient BufferedImage playerImage;

    /**
     * Constructs a PlayerRender with specified screen dimensions.
     *
     * @param screenWidth the reference screen width for scaling calculations
     * @param screenHeight the reference screen height for scaling calculations
     * @throws NullPointerException if resource is null
     */
    public PlayerRender(final int screenWidth, final int screenHeight) {
        super(PLAYER_WIDTH, PLAYER_HEIGHT, screenWidth, screenHeight);
        //this.playerImage = Objects.requireNonNull(resource, "Resource loader cannot be null").loadImage(PLAYER_PATH);
    }

    /**
     * Renders the player entity as a scaled red rectangle.
     *
     * @param g the Graphics2D context to render into
     * @param entity the player entity to render
     * @throws NullPointerException if graphics context is null
     * @throws IllegalArgumentException if entity is not a Mario instance
     */
    @Override
    public void render(final Graphics2D g, final Entity entity) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        if (!canRender(entity)) {
            throw new IllegalArgumentException("Entity must be a Mario instance");
        }
        final int width = getScaledWidth();
        final int height = getScaledHeight();
        g.setColor(Color.RED);
        g.fillRect((int) entity.getPosition().x(), (int) entity.getPosition().y(), width, height);
    }

    /**
     * Determines if this render can handle the specified entity.
     *
     * @param entity the entity to check
     * @return true if the entity is a Mario instance, false otherwise
     */
    @Override
    public boolean canRender(final Entity entity) {
        return entity instanceof Mario;
    }
}
