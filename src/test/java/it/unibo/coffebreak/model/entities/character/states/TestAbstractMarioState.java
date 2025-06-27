package it.unibo.coffebreak.model.entities.character.states;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.impl.model.entities.mario.states.AbstractMarioState;
import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * Tests for {@link AbstractMarioState} base class behavior.
 */
@ExtendWith(MockitoExtension.class)
class TestAbstractMarioState {

    private static final float DELTA_TIME = 0.016f;
    @Mock private MainCharacter mockCharacter;

    private final AbstractMarioState state = new AbstractMarioState() {
        @Override
        public void handleCollision(final MainCharacter character, final  Entity other) {
            // Test implementation
        }
    };

    @Test
    void testDefaultStateBehavior() {
        assertAll(
            () -> assertFalse(state.isExpired()),
            () -> assertFalse(state.canClimb()),
            () -> assertFalse(state.isClimbing()),
            () -> assertDoesNotThrow(() -> state.onEnter(mockCharacter)),
            () -> assertDoesNotThrow(() -> state.onExit(mockCharacter)),
            () -> assertDoesNotThrow(() -> state.update(mockCharacter, DELTA_TIME)),
            () -> assertDoesNotThrow(state::startClimb),
            () -> assertDoesNotThrow(state::stopClimb)
        );
    }
}
