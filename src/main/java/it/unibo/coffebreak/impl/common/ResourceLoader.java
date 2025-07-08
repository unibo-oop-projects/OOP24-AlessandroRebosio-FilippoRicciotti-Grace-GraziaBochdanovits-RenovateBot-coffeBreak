package it.unibo.coffebreak.impl.common;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import it.unibo.coffebreak.api.common.Loader;

/**
 * Concrete implementation of {@link Loader} that caches loaded resources in
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
public final class ResourceLoader implements Loader {

    /**
     * The path to the "Press Start 2P" TrueType font resource within the classpath.
     */
    public static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";

    /**
     * The path to the "JUMP" sound.
     */
    public static final String JUMP_SOUND = "/sfx/jump.wav";

    /**
     * The path to the "Mario" image.
     */
    public static final String MARIO_IMAGE = "/img/mario_sheet.png";

    private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();
    private static final Map<String, Font> FONT_CACHE = new HashMap<>();
    private static final Map<String, Clip> SOUND_CACHE = new HashMap<>();
    private static final Map<String, List<String>> MAP_CACHE = new HashMap<>();

    /**
     * Loads a resource from the specified path using the provided loader function.
     * <p>
     * This method attempts to open an {@link InputStream} for the resource located
     * at the given path.
     * If the resource is found, it applies the provided loader function to the
     * stream and returns the result.
     * If the resource is not found or an error occurs during loading, a
     * {@link ResourceException} is thrown
     * with the appropriate message.
     * </p>
     *
     * @param <T>         the type of the object returned by the loader function
     * @param path        the path to the resource to load
     * @param loader      a function that processes the {@link InputStream} and
     *                    returns an object of type {@code T}
     * @param notFoundMsg the message to use if the resource is not found
     * @param failMsg     the message to use if loading the resource fails for
     *                    another reason
     * @return the object produced by the loader function
     * @throws ResourceException if the resource is not found or loading fails
     */
    private <T> T loadResource(final String path, final Function<InputStream, T> loader,
            final String notFoundMsg, final String failMsg) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new ResourceException(notFoundMsg + path);
            }
            return loader.apply(is);
        } catch (final IOException e) {
            throw new ResourceException(failMsg + path, e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ResourceException if the image cannot be loaded (wraps IOException)
     */
    @Override
    public BufferedImage loadImage(final String path) {
        return IMAGE_CACHE.computeIfAbsent(path, p -> loadResource(p, is -> {
            try {
                final BufferedImage img = ImageIO.read(is);
                if (img == null) {
                    throw new ResourceException("Invalid image format: " + p);
                }
                return img;
            } catch (final IOException e) {
                throw new ResourceException("Failed to load image: " + p, e);
            }
        }, "Resource not found: ", "Failed to load image: "));
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
    public Font loadFont(final String path) {
        return FONT_CACHE.computeIfAbsent(path, k -> loadResource(path, is -> {
            try {
                return Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (final FontFormatException | IOException e) {
                throw new ResourceException("Failed to load font: " + path, e);
            }
        }, "Font not found: ", "Failed to load font: "));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ResourceException if the resource cannot be found or
     *                           loaded
     */
    @Override
    public Clip loadClip(final String path) {
        return SOUND_CACHE.computeIfAbsent(path, p -> loadResource(p, is -> {
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(is)) {
                final Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                return clip;
            } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new ResourceException("Failed to load clip: " + p, e);
            }
        }, "Audio resource not found: ", "Failed to load clip: "));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ResourceException if the resource cannot be found or
     *                           loaded
     */
    @Override
    public List<String> loadMap(final String path) {
        return MAP_CACHE.computeIfAbsent(path, p -> loadResource(p, is -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return reader.lines().toList();
            } catch (final IOException e) {
                throw new ResourceException("Failed to load map: " + p, e);
            }
        }, "Map resource not found: ", "Failed to load map: "));
    }

    /**
     * Clears all cached resources and releases native resources.
     */
    public static void clearCache() {
        IMAGE_CACHE.values().forEach(BufferedImage::flush);
        IMAGE_CACHE.clear();

        FONT_CACHE.clear();

        SOUND_CACHE.values().stream().filter(Clip::isRunning).forEach(Clip::stop);
        SOUND_CACHE.values().forEach(Clip::close);
        SOUND_CACHE.clear();

        MAP_CACHE.clear();
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
