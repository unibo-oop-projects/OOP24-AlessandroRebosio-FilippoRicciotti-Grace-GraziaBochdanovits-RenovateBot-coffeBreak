package it.unibo.coffebreak.view.api.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
     * @return the loaded Font
     * @throws IOException         if the resource cannot be found or read
     * @throws FontFormatException if the font file is in an invalid format
     */
    Font loadFont(String path) throws IOException, FontFormatException;

    /**
     * Loads an audio clip from the specified classpath location.
     * <p>
     * Supported audio formats are implementation dependent but typically include
     * WAV, AIFF, and AU formats.
     * </p>
     *
     * @param path the classpath-relative path to the audio resource
     * @return the loaded {@link Clip}
     * @throws IOException                   if the resource cannot be accessed or
     *                                       read
     * @throws UnsupportedAudioFileException if the audio format is not supported
     * @throws LineUnavailableException      if the system cannot open an audio line
     *                                       for playback
     */
    Clip loadClip(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException;
}
