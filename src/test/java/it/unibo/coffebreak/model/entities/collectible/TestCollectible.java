package it.unibo.coffebreak.model.entities.collectible;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.collectible.AbstractCollectible;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;

/**
 * Test class for {@link AbstractCollectible} and its implementations ({@link Coin}, {@link Hammer}).
 * Verifies the behavior of collectible items in the game.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestCollectible {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(1, 1);

    @Mock private MainCharacter mockPlayer;

    /**
     * Sets up the test environment before each test.
     * Initializes mock objects and test data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests that collecting a {@link Coin} increases the player's score.
     * Verifies that:
     * - The earnPoints method is called on the player
     * - The coin is marked as collected
     */
    @Test
    void testCoinCollectionIncreasesScore() {
        final Coin coin = new Coin(TEST_POSITION, TEST_DIMENSION);

        coin.collect(mockPlayer);

        verify(mockPlayer).earnPoints(anyInt());
        assertTrue(coin.isCollected());
    }

    /**
     * Tests that collecting a {@link Hammer} changes the player's state.
     * Verifies that:
     * - The earnPoints method is called on the player
     * - The changeState method is called with a Supplier that produces a WithHammerState
     * - The hammer is marked as collected
     */
    @Test
    void testHammerCollectionChangesState() {
        final Hammer hammer = new Hammer(TEST_POSITION, TEST_DIMENSION);

        hammer.collect(mockPlayer);

        verify(mockPlayer).earnPoints(anyInt());
        verify(mockPlayer).changeState(argThat(supplier -> {
            final CharacterState state = supplier.get();
            return state instanceof WithHammerState;
        }));
        assertTrue(hammer.isCollected());
    }

    /**
     * Tests that a collectible can only be collected once.
     */
    @Test
    void testCollectibleCannotBeCollectedTwice() {
        final Coin coin = new Coin(TEST_POSITION, TEST_DIMENSION);

        coin.collect(mockPlayer);
        coin.collect(mockPlayer);

        verify(mockPlayer, times(1)).earnPoints(anyInt());
    }

    /**
     * Tests that the onCollision method has no effect on collectibles.
     */
    @Test
    void testOnCollisionDoesNothing() {
        final AbstractCollectible collectible = new Coin(TEST_POSITION, TEST_DIMENSION);

        collectible.onCollision(mockPlayer);

        verifyNoInteractions(mockPlayer);
        assertFalse(collectible.isCollected());
    }
}
