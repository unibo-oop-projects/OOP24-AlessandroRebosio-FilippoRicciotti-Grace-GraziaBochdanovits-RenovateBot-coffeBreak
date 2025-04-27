package it.unibo.coffebreak.model.entity.platform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows; 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.impl.entity.platform.AbstractPlatform;
import it.unibo.coffebreak.model.impl.entity.platform.BasicPlatform;
import it.unibo.coffebreak.model.impl.entity.platform.MovingPlatform;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Test class for {@link MovingPlatform}.
 * Verifies the behavior of a platform that moves according to a velocity vector,
 * including position updates, velocity handling, and integration with base platforms.
 */
@ExtendWith(MockitoExtension.class) 
class TestMovingPlatform {

    /** Initial position for the moving platform. */
    private static final Position TEST_POSITION = new Position(0, 0);

    /** Test dimension for the platform. */
    private static final Dimension TEST_DIMENSION = new Dimension(10, 10);

    /** Test velocity vector for the moving platform. */
    private static final Vector2D PLATFORM_VELOCITY = new Vector2D(1, 2);
    private static final float MODIFIED_VELOCITY = 99.0f;

    /** Friction constants. */
    private static final float EXPECTED_FRICTION = 0.7f;

    /** Time constants in milliseconds. */
    private static final long ONE_SECOND_IN_MS = 1000L;
    private static final float FLOAT_COMPARISON_DELTA = 0.001f;

    /** Mocked base platform used for testing. */
    private AbstractPlatform mockPlatform;

    /** MovingPlatform instance under test. */
    private MovingPlatform movingPlatform;

    /**
     * Sets up the test environment before each test.
     * Creates a mock of AbstractPlatform with default position and dimension.
     * Wraps it in a MovingPlatform with test velocity.
     */
    @BeforeEach
    void setUp() {
        mockPlatform = mock(AbstractPlatform.class);
        when(mockPlatform.getPosition()).thenReturn(TEST_POSITION);
        when(mockPlatform.getDimension()).thenReturn(TEST_DIMENSION);

        movingPlatform = new MovingPlatform(mockPlatform, PLATFORM_VELOCITY);
    }

    /**
     * Verifies MovingPlatform initial state.
     * Tests that position matches wrapped platform, friction is overridden, and velocity is initialized correctly.
     */
    @Test
    void testInitialState() {
        assertEquals(TEST_POSITION, movingPlatform.getPosition());
        assertEquals(EXPECTED_FRICTION, movingPlatform.getFriction());
        assertEquals(PLATFORM_VELOCITY.getX(), movingPlatform.getVelocity().getX());
        assertEquals(PLATFORM_VELOCITY.getY(), movingPlatform.getVelocity().getY());
    }

    /**
     * Verifies constructor throws NullPointerException.
     * Tests that exception is thrown when base platform or velocity vector is null.
     */
    @Test
    void testNullParametersInConstructor() {
        assertThrows(NullPointerException.class, () -> 
            new MovingPlatform(null, PLATFORM_VELOCITY));

        assertThrows(NullPointerException.class, () -> 
            new MovingPlatform(mockPlatform, null));
    }

    /**
     * Tests platform movement after time update.
     * Verifies position changes according to velocity × time and base platform position remains unchanged.
     */
    @Test
    void testPositionUpdate() {
        movingPlatform.update(ONE_SECOND_IN_MS);

        final Position newPosition = movingPlatform.getPosition();
        assertEquals(PLATFORM_VELOCITY.getX(), newPosition.x(), FLOAT_COMPARISON_DELTA);
        assertEquals(PLATFORM_VELOCITY.getY(), newPosition.y(), FLOAT_COMPARISON_DELTA);

        verify(mockPlatform, never()).setPosition(any());
    }

    /**
     * Verifies velocity getter returns defensive copy.
     * Confirms internal velocity remains unchanged when returned copy is modified.
     */
    @Test
    void testVelocityDefensiveCopy() {
        final Vector2D velocityCopy = movingPlatform.getVelocity();
        velocityCopy.setX(MODIFIED_VELOCITY);

        assertEquals(PLATFORM_VELOCITY.getX(), movingPlatform.getVelocity().getX());
    }

    @Test
    void testFrictionIsOverridden() {
        assertEquals(EXPECTED_FRICTION, movingPlatform.getFriction());
        verify(mockPlatform, never()).getFriction();
    }

    @Test
    void testIntegrationWithBasicPlatform() {
        final AbstractPlatform concretePlatform = new BasicPlatform(TEST_POSITION, TEST_DIMENSION);
        final MovingPlatform moving = new MovingPlatform(concretePlatform, PLATFORM_VELOCITY);

        moving.update(1000);
        assertEquals(1.0f, moving.getPosition().x(), FLOAT_COMPARISON_DELTA);
    }

}
