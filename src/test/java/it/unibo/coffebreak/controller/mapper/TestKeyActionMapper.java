package it.unibo.coffebreak.controller.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.controller.mapper.KeyActionMapper;
import it.unibo.coffebreak.impl.controller.mapper.StandardKeyMapper;

/**
 * Unit tests for the {@link KeyActionMapper} interface and its implementation.
 * 
 * @author Alessandro Rebosio
 */
class TestKeyActionMapper {

    private static final int VALUE = 9999;

    private KeyActionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StandardKeyMapper();
    }

    /**
     * Checks that the key mapping is not null and contains all standard keys.
     */
    @Test
    void testGetKeyMappingsNotNullAndContainsStandardKeys() {
        final var mappings = mapper.getKeyMappings();
        assertNotNull(mappings);
        assertTrue(mappings.containsKey(KeyEvent.VK_ENTER));
        assertTrue(mappings.containsKey(KeyEvent.VK_ESCAPE));
        assertTrue(mappings.containsKey(KeyEvent.VK_UP));
        assertTrue(mappings.containsKey(KeyEvent.VK_DOWN));
        assertTrue(mappings.containsKey(KeyEvent.VK_LEFT));
        assertTrue(mappings.containsKey(KeyEvent.VK_RIGHT));
        assertTrue(mappings.containsKey(KeyEvent.VK_SPACE));
    }

    /**
     * Checks that getAction returns the correct Action for each standard key.
     */
    @Test
    void testGetActionReturnsCorrectAction() {
        assertEquals(Action.ENTER, mapper.getAction(KeyEvent.VK_ENTER).orElse(null));
        assertEquals(Action.ESCAPE, mapper.getAction(KeyEvent.VK_ESCAPE).orElse(null));
        assertEquals(Action.UP, mapper.getAction(KeyEvent.VK_UP).orElse(null));
        assertEquals(Action.DOWN, mapper.getAction(KeyEvent.VK_DOWN).orElse(null));
        assertEquals(Action.LEFT, mapper.getAction(KeyEvent.VK_LEFT).orElse(null));
        assertEquals(Action.RIGHT, mapper.getAction(KeyEvent.VK_RIGHT).orElse(null));
        assertEquals(Action.SPACE, mapper.getAction(KeyEvent.VK_SPACE).orElse(null));
    }

    /**
     * Checks that getAction returns an empty Optional for an unmapped key.
     */
    @Test
    void testGetActionReturnsEmptyForUnknownKey() {
        assertTrue(mapper.getAction(VALUE).isEmpty());
    }

    /**
     * Checks that isMapped returns true for mapped keys.
     */
    @Test
    void testIsMappedReturnsTrueForMappedKeys() {
        assertTrue(mapper.isMapped(KeyEvent.VK_ENTER));
        assertTrue(mapper.isMapped(KeyEvent.VK_SPACE));
    }

    /**
     * Checks that isMapped returns false for an unmapped key.
     */
    @Test
    void testIsMappedReturnsFalseForUnmappedKey() {
        assertFalse(mapper.isMapped(VALUE));
    }

    /**
     * Checks that processKeys returns only the correct Actions for mapped keys,
     * ignoring unmapped ones.
     */
    @Test
    void testProcessKeysReturnsCorrectActions() {
        final var keys = List.of(KeyEvent.VK_ENTER, KeyEvent.VK_LEFT, VALUE, KeyEvent.VK_SPACE);
        final var actions = mapper.processKeys(keys.stream()).toList();
        assertEquals(List.of(Action.ENTER, Action.LEFT, Action.SPACE), actions);
    }
}
