package it.unibo.coffebreak.view.impl.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import it.unibo.coffebreak.view.api.resources.Resource;

/**
 * Concrete implementation of {@link Resource} that caches loaded resources in
 * memory.
 * This class handles:
 * <ul>
 * <li>Image loading and validation</li>
 * <li>Font loading and scaling</li>
 * <li>Resource lifecycle management</li>
 * </ul>
 * 
 * <p>
 * Resources are cached indefinitely until explicitly cleared.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public final class ResourceLoader implements Resource {
    private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();
    private static final Map<String, Font> FONT_CACHE = new HashMap<>();
    private static final Map<String, Clip> SOUND_CACHE = new HashMap<>();

    /**
     * Instantiates a new Game resources.
     */
    public ResourceLoader() {
        try {
            loadFont("/fonts/PressStart2P-Regular.ttf");
        } catch (IOException | FontFormatException e) {
            throw new IllegalStateException("Error while loading resources", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ResourceException if the image cannot be loaded (wraps IOException)
     */
    @Override
    public BufferedImage loadImage(final String path) throws IOException {
        return IMAGE_CACHE.computeIfAbsent(path, p -> {
            try (InputStream is = getClass().getResourceAsStream(p)) {
                if (is == null) {
                    throw new ResourceException("Resource not found: " + p);
                }
                final BufferedImage img = ImageIO.read(is);
                if (img == null) {
                    throw new ResourceException("Invalid image format: " + path);
                }
                return img;
            } catch (final IOException e) {
                throw new ResourceException("Failed to load image: " + p, e);
            }
        });
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Fonts are cached using a composite key of path and size.
     * </p>
     * 
     * @throws ResourceException if the font cannot be loaded (wraps IOException or
     *                           FontFormatException)
     */
    @Override
    public Font loadFont(final String path) throws IOException, FontFormatException {
        return FONT_CACHE.computeIfAbsent(path, k -> {
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is == null) {
                    throw new ResourceException("Font not found: " + path);
                }
                return Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (FontFormatException | IOException e) {
                throw new ResourceException("Failed to load font: " + path, e);
            }
        });
    }

    @Override
    public Clip loadClip(final String path)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        return SOUND_CACHE.computeIfAbsent(path, p -> {
            try (InputStream is = getClass().getResourceAsStream(p)) {
                if (is == null) {
                    throw new ResourceException("Audio resource not found: " + p);
                }

                AudioInputStream audioIn = AudioSystem.getAudioInputStream(is);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                return clip;
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                throw new ResourceException("Failed to load clip: " + e);
            }
        });
    }

    /**
     * Clears all cached resources and releases native resources.
     */
    public static void clearCache() {
        IMAGE_CACHE.values().forEach(BufferedImage::flush);
        IMAGE_CACHE.clear();
        FONT_CACHE.clear();
    }

    /**
     * Internal exception class for resource loading errors.
     */
    private static final class ResourceException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new ResourceException with the specified message.
         * 
         * @param message the detail message
         */
        ResourceException(final String message) {
            super(message);
        }

        /**
         * Creates a new ResourceException with the specified message and cause.
         * 
         * @param message the detail message
         * @param cause   the root cause
         */
        ResourceException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
