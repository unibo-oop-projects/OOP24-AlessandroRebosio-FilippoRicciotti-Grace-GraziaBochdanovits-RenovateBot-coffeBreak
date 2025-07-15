package it.unibo.coffebreak.api.common;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.sound.sampled.Clip;

/**
 * Provides an interface for loading and managing graphical resources such as
 * images and fonts.
 * Implementations should handle resource loading, caching, and lifecycle
 * management.
 * 
 * @author Alessandro Rebosio
 */
public interface Loader {

    /**
     * Loads an image from the specified path.
     *
     * @param path the path to the image resource, relative to the classpath
     * @return the loaded BufferedImage
     * @throws RuntimeException if the image cannot be loaded or the format is
     *                          invalid
     */
    BufferedImage loadImage(String path);

    /**
     * Loads a font from the specified path.
     *
     * @param path the path to the font resource, relative to the classpath
     * @return the loaded Font
     * @throws RuntimeException if the font cannot be loaded or the format is
     *                          invalid
     */
    Font loadFont(String path);

    /**
     * Loads an audio clip from the specified classpath location.
     *
     * @param path the classpath-relative path to the audio resource
     * @return the loaded {@link Clip}
     * @throws RuntimeException if the audio resource cannot be loaded
     */
    Clip loadClip(String path);

    /**
     * Loads a map from the specified file path.
     *
     * @param path the path to the file containing the map data
     * @return a list of strings representing the loaded map
     * @throws RuntimeException if the map resource cannot be loaded
     */
    List<String> loadMap(String path);

}
