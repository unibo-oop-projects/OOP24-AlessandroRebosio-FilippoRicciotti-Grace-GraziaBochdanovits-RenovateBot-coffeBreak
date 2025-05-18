package it.unibo.coffebreak.view.api.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Provides an interface for loading and managing graphical resources such as
 * images and fonts.
 * Implementations should handle resource loading, caching, and lifecycle
 * management.
 * 
 * @author Alessandro Rebosio
 */
public interface Resource {
    /**
     * Loads an image from the specified path.
     *
     * @param path the path to the image resource, relative to the classpath
     * @return the loaded BufferedImage
     * @throws IOException              if the resource cannot be found or read
     * @throws IllegalArgumentException if the image format is invalid
     */
    BufferedImage loadImage(String path) throws IOException;

    /**
     * Loads a font from the specified path with the given size.
     *
     * @param path the path to the font resource, relative to the classpath
     * @param size the point size of the font
     * @return the loaded Font
     * @throws IOException         if the resource cannot be found or read
     * @throws FontFormatException if the font file is in an invalid format
     */
    Font loadFont(String path, float size) throws IOException, FontFormatException;
}
