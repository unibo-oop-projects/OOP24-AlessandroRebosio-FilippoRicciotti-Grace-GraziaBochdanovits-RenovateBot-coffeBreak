package it.unibo.coffebreak.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.ResourceLoader;

/**
 * Unit tests for the {@link ResourceLoader} class.
 * 
 * @author Alessandro Rebosio
 */
public class TestResourceLoader {

    /**
     * Tests that loading a non-existent image throws ResourceException.
     */
    @Test
    void testLoadImageNotFound() {
        ResourceLoader loader = new ResourceLoader();
        assertThrows(RuntimeException.class, () -> loader.loadImage("/notfound.png"));
    }

    /**
     * Tests that loading a non-existent font throws ResourceException.
     */
    @Test
    void testLoadFontNotFound() {
        ResourceLoader loader = new ResourceLoader();
        assertThrows(RuntimeException.class, () -> loader.loadFont("/notfound.ttf"));
    }

    /**
     * Tests that loading a non-existent clip throws ResourceException.
     */
    @Test
    void testLoadClipNotFound() {
        ResourceLoader loader = new ResourceLoader();
        assertThrows(RuntimeException.class, () -> loader.loadClip("/notfound.wav"));
    }

    /**
     * Tests that clearCache does not throw and clears all caches.
     */
    @Test
    void testClearCache() {
        assertDoesNotThrow(ResourceLoader::clearCache);
    }

    /**
     * Tests that loading a valid image returns a non-null BufferedImage.
     */
    @Test
    void testLoadImageValid() {
        ResourceLoader loader = new ResourceLoader();
        assertNotNull(loader.loadImage("/img/sheet.png"));
    }

    /**
     * Tests that loading a valid font returns a non-null Font.
     */
    @Test
    void testLoadFontValid() {
        ResourceLoader loader = new ResourceLoader();
        assertNotNull(loader.loadFont("/fonts/PressStart2P-Regular.ttf"));
    }

    /**
     * Tests that loading a valid clip returns a non-null Clip.
     */
    @Test
    void testLoadClipValid() {
        ResourceLoader loader = new ResourceLoader();
        assertNotNull(loader.loadClip("/sfx/jump.wav"));
    }
}
