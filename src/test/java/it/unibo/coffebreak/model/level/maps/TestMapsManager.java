package it.unibo.coffebreak.model.level.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.common.ResourceLoader;
import it.unibo.coffebreak.impl.model.level.maps.GameMapsManager;

/**
 * Unit tests for the {@link GameMapsManager} class.
 * Verifies map progression and reset behavior.
 * 
 * @author Filippo Ricciotti
 */
class TestMapsManager {

    private static final String MAP_PATH_PREFIX = "/maps/Map";
    private Loader loader;
    private GameMapsManager mapsManager;

    @BeforeEach
    void setUp() {
        loader = new ResourceLoader();
        mapsManager = new GameMapsManager(loader);
    }

    /**
     * Verifies that the current map changes after calling advance
     * and that reset brings the manager back to the first map.
     */
    @Test
    void shouldAdvanceAndResetCorrectly() {
        List<String> initialMap = mapsManager.currentMap();

        // Simulate a dummy entity list for advance()
        final List<Entity> dummyEntities = List.of();

        // Advance through all maps until cannot advance anymore
        while (mapsManager.advance(dummyEntities)) {
            final List<String> nextMap = mapsManager.currentMap();
            assertNotEquals(initialMap, nextMap, "Current map should change after advance.");
            initialMap = nextMap;
        }

        // After full advance, calling advance again should not change map anymore
        final List<String> lastMapBeforeFail = mapsManager.currentMap();
        final boolean result = mapsManager.advance(dummyEntities);
        assertFalse(result, "Should not advance beyond last map.");
        assertEquals(lastMapBeforeFail, mapsManager.currentMap(), "Map should remain the same after failed advance.");

        // Reset and verify if we're back to the first map
        mapsManager.reset();
        assertEquals(loader.loadMap(MAP_PATH_PREFIX + "1.txt"), mapsManager.currentMap(),
                "After reset, should return to the first map.");
    }

    /**
     * Verifies that calling canDonkeyThrowBarrel does not throw exceptions
     * and returns a valid boolean value for each map state.
     */
    @Test
    void shouldReturnCanDonkeyThrowBarrelFlag() {
        final List<Entity> dummyEntities = List.of();

        do {
            final boolean result = mapsManager.canDonkeyThrowBarrel();
            assertNotNull(result);
        } while (mapsManager.advance(dummyEntities));
    }
}
