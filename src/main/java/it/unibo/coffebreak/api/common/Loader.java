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
     * 
     * @throws IllegalArgumentException if the image format is invalid
     */
    BufferedImage loadImage(String path);

    /**
     * Loads a font from the specified path with the given size.
     *
     * @param path the path to the font resource, relative to the classpath
     * 
     * @return the loaded Font
     */
    Font loadFont(String path);

    /**
     * Loads an audio clip from the specified classpath location.
     * <p>
     * Supported audio formats are implementation dependent but typically include
     * WAV, AIFF, and AU formats.
     * </p>
     *
     * @param path the classpath-relative path to the audio resource
     * 
     * @return the loaded {@link Clip}
     */
    Clip loadClip(String path);

    /**
     * Loads a map from the specified file path.
     *
     * @param path the path to the file containing the map data
     * @return a list of strings representing the loaded map
     * @throws IOException if an I/O error occurs while reading the file
     */
    List<String> loadMap(String path);

}
