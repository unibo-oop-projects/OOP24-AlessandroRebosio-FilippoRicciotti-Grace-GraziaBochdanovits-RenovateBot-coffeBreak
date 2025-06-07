package it.unibo.coffebreak.view.resources;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

/**
 * Test class for {@link ResourceLoader} functionality.
 * <p>
 * This class verifies the correct behavior of the resource loading system,
 * including font loading, caching mechanism, and error handling.
 * 
 * @author Alessandro Rebosio
 */
class TestResourceLoader {

    /** Path to a non-existent resource for negative testing. */
    private static final String INVALID_PATH = "/invalid/path.txt";

    /** The ResourceLoader instance under test. */
    private ResourceLoader loader;

    /**
     * Initializes the test environment before each test method execution.
     */
    @BeforeEach
    void setUp() {
        ResourceLoader.clearCache();
        this.loader = new ResourceLoader();
    }

    /**
     * Tests successful font loading functionality.
     * 
     * @throws IOException         if an I/O error occurs during font loading
     * @throws FontFormatException if the font data does not conform to the expected
     *                             format
     */
    @Test
    void testFontLoading() throws IOException, FontFormatException {
        final Font font = this.loader.loadFont(ResourceLoader.FONT_PATH);
        assertNotNull(font);
        assertEquals("Press Start 2P Regular", font.getName());

        final Font cachedFont = this.loader.loadFont(ResourceLoader.FONT_PATH);
        assertSame(font, cachedFont);
    }

    /**
     * Tests successful imgae loading functionality.
     * 
     * @throws IOException if an I/O error occurs during font loading
     */
    @Test
    void testImageLoading() throws IOException {
        final BufferedImage image = this.loader.loadImage(ResourceLoader.MARIO_IMAGE);

        assertNotNull(image, "Loaded image should not be null");
        assertTrue(image.getWidth() > 0, "Image should have positive width");
        assertTrue(image.getHeight() > 0, "Image should have positive height");

        final BufferedImage cachedImgae = this.loader.loadImage(ResourceLoader.MARIO_IMAGE);
        assertSame(image, cachedImgae);
    }

    /**
     * Tests successful sound loading functionality.
     * 
     * @throws IOException                   if an I/O error occurs during font
     *                                       loading
     * @throws UnsupportedAudioFileException if the font data does not conform to
     *                                       the expected
     *                                       format
     * @throws LineUnavailableException
     */
    @Test
    void testSoundLoading() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        final Clip clip = this.loader.loadClip(ResourceLoader.JUMP_SOUND);

        assertNotNull(clip, "Clip should not be null after loading");
        assertFalse(clip.isRunning(), "Clip should not be running immediately after loading");
        assertTrue(clip.isOpen(), "Clip should be open after loading");

        final Clip chacedClip = this.loader.loadClip(ResourceLoader.JUMP_SOUND);
        assertSame(clip, chacedClip);
    }

    /**
     * Tests handling of invalid font paths.
     */
    @Test
    void testInvalidFontPath() {
        final Exception exception = assertThrows(RuntimeException.class, () -> this.loader.loadFont(INVALID_PATH));
        assertTrue(exception.getMessage().contains("Font not found"));
    }

    /**
     * Tests handling of invalid image paths.
     */
    @Test
    void testInvalidImagePath() {
        final Exception exception = assertThrows(RuntimeException.class, () -> this.loader.loadImage(INVALID_PATH));
        assertTrue(exception.getMessage().contains("Resource not found"));
    }

    /**
     * Tests handling of invalid image paths.
     */
    @Test
    void testInvalidClipPath() {
        final Exception exception = assertThrows(RuntimeException.class, () -> this.loader.loadClip(INVALID_PATH));
        assertTrue(exception.getMessage().contains("Audio resource not found"));
    }

    /**
     * Tests default font loading during ResourceLoader construction.
     */
    @Test
    void testDefaultFontLoadingOnConstruction() {
        assertDoesNotThrow(() -> new ResourceLoader());
    }
}
