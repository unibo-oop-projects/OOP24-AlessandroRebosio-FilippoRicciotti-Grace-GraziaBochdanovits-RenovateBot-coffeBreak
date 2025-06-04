package it.unibo.coffebreak.view.impl.renders;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.coffebreak.view.api.renders.StaticRender;
import it.unibo.coffebreak.view.impl.resources.ResourceLoader;

/**
 * A renderer implementation for drawing game levels from a sprite sheet.
 * This class loads a level sprite sheet and renders specific level sub-images
 * based on the current level index. The levels are arranged in a grid pattern
 * within the sprite sheet.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class LevelRender extends AbstractScalableRender implements StaticRender {

    private static final String BACKGROUND_PATH_PREFIX = "/img/level";
    private static final String BACKGROUND_PATH_SUFFIX = "_background_sheet.png";
    private static final int LEVEL_WIDTH = 256;
    private static final int LEVEL_HEIGHT = 240;
    private static final int Z_INDEX = 0; // Lowest layer value
    private final transient BufferedImage background;

    /**
     * Constructs a new LevelRender that loads the level sprite sheet.
     * 
     * @param resources the ResourceLoader used to access the image resources
     * @param currentLevelIndex the number of the level
     */
    public LevelRender(final ResourceLoader resources, final int currentLevelIndex) {
        super(LEVEL_WIDTH, LEVEL_HEIGHT, 0, 0);
        final String backgroundPath = BACKGROUND_PATH_PREFIX + currentLevelIndex + BACKGROUND_PATH_SUFFIX;
        this.background = Objects.requireNonNull(resources).loadImage(backgroundPath);
    }

    /**
     * Renders the current level image at position (1, 0) in the game window.
     * The specific level image is extracted from the sprite sheet based on the
     * current level index.
     * 
     * @param g the Graphics2D context used for drawing operations
     */
    @Override
    public void render(final Graphics2D g) {
        g.drawImage(background, 0, 0, getScaledWidth(), getScaledHeight(),
                      0, 0, getOriginalWidth(), getOriginalHeight(), null);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getZIndex() {
        return Z_INDEX;
    }
}
