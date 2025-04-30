package it.unibo.coffebreak.model.entity.platform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.impl.entity.platform.AbstractPlatform;
import it.unibo.coffebreak.model.impl.entity.platform.PlatformDecorator;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link PlatformDecorator}.
 * Verifies the decorator pattern implementation for platform entities,
 * ensuring proper delegation of methods to the wrapped platform instance
 * and validation of constructor parameters.
 */
@ExtendWith(MockitoExtension.class) 
class TestPlatformDecorator {

    private static final Position TEST_POSITION = new Position(10, 20);
    private static final Dimension TEST_DIMENSION = new Dimension(30, 40);

    /** Friction Constants. */
    private static final float TEST_FRICTION = 0.3f;
    private static final float EXPECTED_FRICTION = TEST_FRICTION;

    /** Mocked base platform used for testing the decorator. */
    private AbstractPlatform mockPlatform;

    /** PlatformDecorator instance under test. */
    private PlatformDecorator decorator;

    /**
     * Initializes the test environment before each test.
     * <p>
     * Creates and configures:
     * <ul>
     *   <li>A mock platform with predefined position and dimension</li>
     *   <li>A PlatformDecorator instance wrapping the mock platform</li>
     * </ul>
     */
    @BeforeEach
    void setUp() {
        mockPlatform = Mockito.mock(AbstractPlatform.class);
        when(mockPlatform.getPosition()).thenReturn(TEST_POSITION);
        when(mockPlatform.getDimension()).thenReturn(TEST_DIMENSION);

        decorator = new PlatformDecorator(mockPlatform) { };
    }

    /**
     * Verifies method delegation to wrapped platform.
     * <p>
     * Tests that:
     * <ul>
     *   <li>{@code canBreak()} returns value from wrapped platform</li>
     *   <li>{@code getFriction()} returns value from wrapped platform</li>
     *   <li>Methods are properly delegated (verified via Mockito)</li>
     * </ul>
     */
    @Test
    void testDelegatedMethods() {
        when(mockPlatform.canBreak()).thenReturn(true);
        when(mockPlatform.getFriction()).thenReturn(TEST_FRICTION);

        assertTrue(decorator.canBreak());
        assertEquals(EXPECTED_FRICTION, decorator.getFriction());
        verify(mockPlatform, times(1)).canBreak(); 
    }

    /**
     * Validates constructor null-check behavior.
     * <p>
     * Verifies that:
     * <ul>
     *   <li>Constructor throws {@code NullPointerException} when given null platform</li>
     * </ul>
     */
    @Test
    void testNullPlatformValidation() {
        assertThrows(NullPointerException.class, () -> {
            new PlatformDecorator(null) { };
        });
    }
}
