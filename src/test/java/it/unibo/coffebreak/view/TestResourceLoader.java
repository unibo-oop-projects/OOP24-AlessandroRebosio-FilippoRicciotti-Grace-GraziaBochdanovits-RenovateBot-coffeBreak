package it.unibo.coffebreak.view;

import it.unibo.coffebreak.view.impl.resources.ResourceLoader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // TODO: implements image test loading
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
     * Tests default font loading during ResourceLoader construction.
     */
    @Test
    void testDefaultFontLoadingOnConstruction() {
        assertDoesNotThrow(() -> new ResourceLoader());
    }
}
