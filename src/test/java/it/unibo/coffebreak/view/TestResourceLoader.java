package it.unibo.coffebreak.view;

import it.unibo.coffebreak.view.impl.resources.ResourceLoader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestResourceLoader {

    private ResourceLoader loader;
    private static final String TEST_FONT = "/fonts/PressStart2P-Regular.ttf";
    private static final String INVALID_PATH = "/invalid/path.txt";

    @BeforeEach
    void setUp() {
        ResourceLoader.clearCache();
        this.loader = new ResourceLoader();
    }

    @Test
    void testFontLoading() throws Exception {
        final Font font = this.loader.loadFont(TEST_FONT);
        assertNotNull(font);
        assertEquals("Press Start 2P Regular", font.getName());

        // Verifica caching
        final Font cachedFont = this.loader.loadFont(TEST_FONT);
        assertSame(font, cachedFont);
    }

    @Test
    void testInvalidFontPath() {
        final Exception exception = assertThrows(RuntimeException.class, () -> this.loader.loadFont(INVALID_PATH));
        assertTrue(exception.getMessage().contains("Font not found"));
    }

    @Test
    void testInvalidImagePath() {
        final Exception exception = assertThrows(IOException.class, () -> this.loader.loadImage(INVALID_PATH));
        assertTrue(exception.getMessage().contains("Resource not found"));
    }

    @Test
    void testDefaultFontLoadingOnConstruction() {
        assertDoesNotThrow(() -> new ResourceLoader());
    }
}
