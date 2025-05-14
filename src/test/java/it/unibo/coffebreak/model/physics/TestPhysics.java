package it.unibo.coffebreak.model.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.common.Vector2D;
import it.unibo.coffebreak.model.impl.physics.GamePhysics;

class TestPhysics {

    private static final float DELTA_TIME = 0.016f;

    private Physics gamePhysics;

    @BeforeEach
    void setUp() {
        gamePhysics = new GamePhysics();
    }

    @Test
    void testCalculateXMoveRight() {
        final Vector2D result = gamePhysics.calculateX(DELTA_TIME, Command.MOVE_RIGHT);
        final Vector2D expected = new Vector2D(GamePhysics.BASE_SPEED * DELTA_TIME, 0f);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateXMoveLeft() {
        final Vector2D result = gamePhysics.calculateX(DELTA_TIME, Command.MOVE_LEFT);
        final Vector2D expected = new Vector2D(-GamePhysics.BASE_SPEED * DELTA_TIME, 0f);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateXOtherCommand() {
        final Vector2D result = gamePhysics.calculateX(DELTA_TIME, Command.JUMP);
        assertEquals(GamePhysics.ZERO_VECTOR, result);
    }

    @Test
    void testCalculateYMoveUp() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.MOVE_UP);
        final Vector2D expected = new Vector2D(0f, GamePhysics.BASE_SPEED * DELTA_TIME);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateYMoveDown() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.MOVE_DOWN);
        final Vector2D expected = new Vector2D(0f, -GamePhysics.BASE_SPEED * DELTA_TIME);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateYJump() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.JUMP);
        final Vector2D expected = new Vector2D(0f, GamePhysics.JUMP_FORCE * DELTA_TIME);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateYDefaultGravity() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.MOVE_RIGHT);
        final Vector2D expected = new Vector2D(0f, -GamePhysics.BASE_SPEED * DELTA_TIME);
        assertEquals(expected, result);
    }

    @Test
    void testZeroVectorIsImmutable() {
        final Vector2D modified = new Vector2D(GamePhysics.ZERO_VECTOR.x() + 1, GamePhysics.ZERO_VECTOR.y() + 1);
        assertNotEquals(GamePhysics.ZERO_VECTOR, modified);
    }

    @Test
    void testDifferentDeltaTimes() {
        final float customDeltaTime = 0.5f;
        final Vector2D result = gamePhysics.calculateX(customDeltaTime, Command.MOVE_RIGHT);
        final Vector2D expected = new Vector2D(GamePhysics.BASE_SPEED * customDeltaTime, 0f);
        assertEquals(expected, result);
    }
}
