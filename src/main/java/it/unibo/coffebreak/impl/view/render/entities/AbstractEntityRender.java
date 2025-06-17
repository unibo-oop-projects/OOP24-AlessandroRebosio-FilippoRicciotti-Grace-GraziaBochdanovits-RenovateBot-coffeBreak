package it.unibo.coffebreak.impl.view.render.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;

/**
 * An abstract implementation of {@link EntityRender} that provides basic
 * functionality for rendering entities with screen-relative scaling and
 * sprite sheet background cleaning.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEntityRender implements EntityRender {

    private static final String PATH = "/img/DonkeyKong_sheet.png";

    private final Loader resource;
    private final BufferedImage spriteSheet;

    /**
     * Constructs a new AbstractEntityRender with the specified screen dimensions.
     * The scaling will be calculated relative to these dimensions.
     * 
     * Also removes the background color from the sprite sheet, replacing it with transparency.
     *
     * @param resource the resource loader used to load the sprite sheet
     */
    public AbstractEntityRender(final Loader resource) {
        this.resource = Objects.requireNonNull(resource);
        final BufferedImage spriteSheet = this.resource.loadImage(PATH);

        final Color backgroundColor = new Color(spriteSheet.getRGB(2, 2), true);
        this.spriteSheet = removeBackgroundColor(spriteSheet, backgroundColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime,
                     final int width, final int height) {
        g.drawRect((int) entity.getPosition().x(), 
                   (int) entity.getPosition().y(),
                   entity.getDimension().width(), 
                   entity.getDimension().height());
    }

    /**
     * Returns the resource loader used by this view state.
     * <p>
     * Intended for use only by subclasses to load fonts, images, or sounds.
     * </p>
     *
     * @return the {@link Loader} instance for this view
     */
    protected final Loader getResource() {
        return this.resource;
    }

    /**
     * Returns the sprite sheet image with the background removed.
     *
     * @return the cleaned {@link BufferedImage} of the sprite sheet
     */
    protected final BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    /**
     * Removes a specific background color from an image,
     * replacing it with transparency.
     *
     * @param image           the input image
     * @param backgroundColor the color to be removed
     * @return a new {@link BufferedImage} with the background removed
     */
    private BufferedImage removeBackgroundColor(final BufferedImage image, final Color backgroundColor) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int pixel = image.getRGB(x, y);
                final Color currentColor = new Color(pixel, true);

                if (colorsAreClose(currentColor, backgroundColor)) {
                    newImage.setRGB(x, y, 0x00000000);
                } else {
                    newImage.setRGB(x, y, pixel);
                }
            }
        }
        return newImage;
    }

    /**
     * Determines whether two colors are similar within a given RGB tolerance.
     *
     * @param c1 first color
     * @param c2 second color
     * @return true if the colors are similar, false otherwise
     */
    private boolean colorsAreClose(final Color c1, final Color c2) {
        final int tolerance = 10;
        return Math.abs(c1.getRed() - c2.getRed()) < tolerance
                && Math.abs(c1.getGreen() - c2.getGreen()) < tolerance
                && Math.abs(c1.getBlue() - c2.getBlue()) < tolerance;
    }
}
