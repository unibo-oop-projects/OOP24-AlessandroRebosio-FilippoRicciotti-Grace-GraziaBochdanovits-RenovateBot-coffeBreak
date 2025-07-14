package it.unibo.coffebreak.model.entities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;

/**
 * Test class for {@link AbstractEntity} implementation.
 * Verifies basic entity functionality including:
 * <ul>
 *   <li>Position management</li>
 *   <li>Dimension handling</li>
 *   <li>Velocity control</li>
 *   <li>Collision detection</li>
 * </ul>
 * @author Grazia Bochdanovits de Kavna
 */
class TestAbstractEntity {

    // Test constants
    private static final int INITIAL_X = 10;
    private static final int INITIAL_Y = 20;
    private static final int INITIAL_WIDTH = 5;
    private static final int INITIAL_HEIGHT = 5;
    private static final int NEW_X = 15;
    private static final int NEW_Y = 25;
    private static final int LARGE_WIDTH = 10;
    private static final int LARGE_HEIGHT = 10;
    private static final float VELOCITY_X = 2.5f;
    private static final float VELOCITY_Y = 3.5f;
    private static final float DELTA_TIME = 0.1f;
    private static final int COLLISION_OFFSET_X = 2;
    private static final int COLLISION_OFFSET_Y = 2;
    private static final int NON_COLLISION_OFFSET_X = 10;
    private static final int NON_COLLISION_OFFSET_Y = 10;

    private static final Position INITIAL_POS = new Position(INITIAL_X, INITIAL_Y);
    private static final BoundigBox INITIAL_DIM = new BoundigBox(INITIAL_WIDTH, INITIAL_HEIGHT);
    private AbstractEntity entity;

    @BeforeEach
    void setUp() {
        entity = new TestEntity(INITIAL_POS, INITIAL_DIM);
    }

    /**
     * Tests entity initialization with valid parameters.
     */
    @Test
    void testInitialization() {
        assertEquals(INITIAL_POS, entity.getPosition());
        assertEquals(INITIAL_DIM, entity.getDimension());
        assertEquals(new Vector(), entity.getVelocity());
    }

    /**
     * Tests constructor with null position.
     * Verifies NullPointerException is thrown.
     */
    @Test
    void testConstructorWithNullPosition() {
        assertThrows(NullPointerException.class, () -> 
            new TestEntity(null, INITIAL_DIM));
    }

    /**
     * Tests constructor with null dimension.
     * Verifies NullPointerException is thrown.
     */
    @Test
    void testConstructorWithNullDimension() {
        assertThrows(NullPointerException.class, () -> 
            new TestEntity(INITIAL_POS, null));
    }

    /**
     * Tests position setting and getting.
     */
    @Test
    void testPositionManagement() {
        final Position newPos = new Position(NEW_X, NEW_Y);
        entity.setPosition(newPos);
        assertEquals(newPos, entity.getPosition());

        final Position differentPos = new Position(NEW_X + 5, NEW_Y + 5);
        assertNotEquals(differentPos, entity.getPosition());
    }

    /**
     * Tests dimension setting and getting.
     */
    @Test
    void testDimensionManagement() {
        final BoundigBox newDim = new BoundigBox(LARGE_WIDTH, LARGE_HEIGHT);
        entity.setDimension(newDim);
        assertEquals(newDim, entity.getDimension());

        final BoundigBox differentDim = new BoundigBox(LARGE_WIDTH + 5, LARGE_HEIGHT + 5);
        assertNotEquals(differentDim, entity.getDimension());
    }

    /**
     * Tests velocity setting and getting.
     */
    @Test
    void testVelocityManagement() {
        final Vector newVelocity = new Vector(VELOCITY_X, VELOCITY_Y);
        entity.setVelocity(newVelocity);
        assertEquals(newVelocity, entity.getVelocity());

        final Vector differentVelocity = new Vector(VELOCITY_X * 2, VELOCITY_Y * 2);
        assertNotEquals(differentVelocity, entity.getVelocity());
    }

    /**
     * Tests collision detection between two entities.
     */
    @Test
    void testCollisionDetection() {
        final Entity collidingEntity = new TestEntity(
            new Position(INITIAL_X + COLLISION_OFFSET_X, INITIAL_Y + COLLISION_OFFSET_Y), 
            INITIAL_DIM
        );
        assertTrue(entity.collidesWith(collidingEntity));

        final Entity nonCollidingEntity = new TestEntity(
            new Position(INITIAL_X + NON_COLLISION_OFFSET_X, INITIAL_Y + NON_COLLISION_OFFSET_Y),
            INITIAL_DIM
        );
        assertFalse(entity.collidesWith(nonCollidingEntity));

        final Entity edgeCaseEntity = new TestEntity(
            new Position(INITIAL_X + INITIAL_WIDTH, INITIAL_Y),
            INITIAL_DIM
        );
        assertTrue(entity.collidesWith(edgeCaseEntity));
    }

    /**
     * Tests default update implementation.
     * Verifies no position change occurs with default implementation.
     */
    @Test
    void testDefaultUpdate() {
        final Position originalPos = entity.getPosition();
        entity.update(DELTA_TIME);
        assertEquals(originalPos, entity.getPosition());
    }

    /**
     * Tests default onCollision implementation.
     * Verifies no exception is thrown.
     */
    @Test
    void testDefaultOnCollision() {
        final Entity other = new TestEntity(
            new Position(INITIAL_X + COLLISION_OFFSET_X, INITIAL_Y + COLLISION_OFFSET_Y),
            INITIAL_DIM
        );
        assertDoesNotThrow(() -> entity.onCollision(other));
    }

    /**
     * Concrete implementation of AbstractEntity for testing purposes.
     */
    private static class TestEntity extends AbstractEntity {
        TestEntity(final Position position, final BoundigBox dimension) {
            super(position, dimension);
        }
    }
}
