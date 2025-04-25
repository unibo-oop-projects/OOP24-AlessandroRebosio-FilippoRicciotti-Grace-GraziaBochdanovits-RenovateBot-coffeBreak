package it.unibo.coffebreak.model.entity.platform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.api.entity.platform.Platform;
import it.unibo.coffebreak.model.impl.entity.platform.AbstractPlatform;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link AbstractPlatform}.
 * This class tests the functionality and behavior of the AbstractPlatform implementation.
 */
@ExtendWith(MockitoExtension.class) 
class TestAbstractPlatform {

    private static final Position TEST_POSITION = new Position(10, 20);
    private static final Dimension TEST_DIMENSION = new Dimension(30, 40);
    private static final float FRICTION = 0.8f;
    private AbstractPlatform platform;

    /**
     * Sets up the test environment before each test.
     * Initializes an anonymous implementation of AbstractPlatform with test position and dimension.
     */
    @BeforeEach
    void setUp() {
        platform = new AbstractPlatform(TEST_POSITION, TEST_DIMENSION) {

            @Override
            public boolean canBreak() {
                return platform.canBreak();
            }

            @Override
            public float getFriction() {
                return platform.getFriction();
            }
        };
    }

    /**
     * Tests the initialization of the AbstractPlatform.
     * Verifies that the platform is not null and that its position and dimension
     * are correctly set during initialization.
     */
    @Test
    void testInitialization() {
        assertNotNull(platform);
        assertEquals(TEST_POSITION, platform.getPosition());
        assertEquals(TEST_DIMENSION, platform.getDimension());
    }

    /**
     * Tests the abstract methods of the Platform interface using a mock.
     * Verifies that the canBreak() and getFriction() methods behave as expected
     * when implemented by a concrete class (simulated here with a mock).
     */
    @Test
    void testAbstractMethodsWithMock() {
        final Platform mockPlatform = Mockito.mock(Platform.class);
        when(mockPlatform.canBreak()).thenReturn(true);
        when(mockPlatform.getFriction()).thenReturn(FRICTION);

        assertTrue(mockPlatform.canBreak());
        assertEquals(FRICTION, mockPlatform.getFriction());
    }
}
