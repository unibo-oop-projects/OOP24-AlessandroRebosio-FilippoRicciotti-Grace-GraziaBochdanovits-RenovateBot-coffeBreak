package it.unibo.coffebreak.impl.view.render.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;

/**
 * An abstract implementation of {@link EntityRender} that provides basic
 * functionality for rendering entities with screen-relative scaling and
 * sprite sheet background cleaning.
 * 
 * This class also removes unwanted background colors from the loaded sprite sheet.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEntityRender implements EntityRender {

    private static final String PATH = "/img/sheet.png";

    private final Loader resource;
    private final BufferedImage spriteSheet;

    /**
     * Constructs a new AbstractEntityRender and prepares the cleaned sprite sheet.
     *
     * @param resource the resource loader used to load the sprite sheet
     */
    public AbstractEntityRender(final Loader resource) {
        this.resource = Objects.requireNonNull(resource);
        final BufferedImage rawSheet = this.resource.loadImage(PATH);

        final Color color1 = new Color(rawSheet.getRGB(2, 2), true);
        final Color color2 = new Color(rawSheet.getRGB(0, 0), true);
        this.spriteSheet = makeTransparent(rawSheet, color1, color2);
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
     * @return the {@link Loader} used to load resources
     */
    protected final Loader getResource() {
        return this.resource;
    }

    /**
     * @return the cleaned sprite sheet image
     */
    protected final BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    /**
     * Flips an image horizontally.
     *
     * @param image the image to flip
     * @return the horizontally flipped image
     * @throws NullPointerException if image is null
     */
    protected BufferedImage flipImageHorizontally(final BufferedImage image) {
        Objects.requireNonNull(image, "Image cannot be null");
        final int w = image.getWidth();
        final int h = image.getHeight();

        final BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = flipped.createGraphics();
        g2d.drawImage(image, w, 0, -w, h, null);
        g2d.dispose();
        return flipped;
    }

    /**
     * Makes pixels with specified background colors fully transparent.
     *
     * @param image the input sprite sheet
     * @param bgColors one or more colors to remove
     * @return a new image with the specified colors removed (transparent)
     */
    private BufferedImage makeTransparent(final BufferedImage image, final Color... bgColors) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage transparentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final Color pixelColor = new Color(image.getRGB(x, y), true);
                boolean shouldBeTransparent = false;
                for (final Color bgColor : bgColors) {
                    if (colorsAreClose(pixelColor, bgColor)) {
                        shouldBeTransparent = true;
                        break;
                    }
                }
                if (shouldBeTransparent) {
                    transparentImage.setRGB(x, y, 0x00000000);
                } else {
                    transparentImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return transparentImage;
    }

    /**
     * Determines if two colors are similar within a small RGB tolerance.
     *
     * @param c1 first color
     * @param c2 second color
     * @return true if similar; false otherwise
     */
    private boolean colorsAreClose(final Color c1, final Color c2) {
        final int tolerance = 10;
        return Math.abs(c1.getRed() - c2.getRed()) < tolerance
            && Math.abs(c1.getGreen() - c2.getGreen()) < tolerance
            && Math.abs(c1.getBlue() - c2.getBlue()) < tolerance;
    }
}
