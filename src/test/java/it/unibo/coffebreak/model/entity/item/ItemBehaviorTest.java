package it.unibo.coffebreak.model.entity.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.api.entity.item.Collectible;
import it.unibo.coffebreak.model.impl.entity.mario.MarioState;
import it.unibo.coffebreak.model.impl.entity.item.GameCollectibleFactory;
import it.unibo.coffebreak.model.impl.entity.item.ItemType;

class ItemBehaviorTest {

    /** Test X coordinate for item position. */
    private static final int X = 10;

    /** Test Y coordinate for item position. */
    private static final int Y = 20;

    /** Test width for item dimension. */
    private static final int WIDTH = 30;

    /** Test height for item dimension. */
    private static final int HEIGTH = 40;

    private GameCollectibleFactory factory;
    private Position testPosition;
    private Dimension testDimension;
    private Mario mockMario;

    @BeforeEach
    void setUp() {
        factory = new GameCollectibleFactory();
        testPosition = new Position(X, Y);
        testDimension = new Dimension(WIDTH, HEIGTH);
        mockMario = mock(Mario.class);
        when(mockMario.isAlive()).thenReturn(true);
    }

    @Test
    void testHammerEffectOnMario() {
        final Collectible hammer = factory.createHammer(testPosition, testDimension);
        hammer.collect(mockMario);

        verify(mockMario).changeState(MarioState.WITH_HAMMER);
    }

    @Test
    void testHammerEffectOnDeadMario() {
        when(mockMario.isAlive()).thenReturn(false);

        final Collectible hammer = factory.createHammer(testPosition, testDimension);
        hammer.collect(mockMario);

        verify(mockMario, never()).changeState(any());
    }

    @Test
    void testCoinEffect() {
        final Collectible coin = factory.createCoin(testPosition, testDimension);
        coin.collect(mockMario);

        verifyNoInteractions(mockMario);
    }

    @Test
    void testBagEffect() {
        final Collectible bag = factory.createBag(testPosition, testDimension);
        bag.collect(mockMario);

        verifyNoInteractions(mockMario);
    }

    @Test
    void testHatEffect() {
        final Collectible hat = factory.createHat(testPosition, testDimension);
        hat.collect(mockMario);

        verifyNoInteractions(mockMario);
    }

    @Test
    void testItemProperties() {
        final Collectible hammer = factory.createHammer(testPosition, testDimension);

        assertEquals(ItemType.HAMMER.getValue(), hammer.getValue());
        assertFalse(hammer.isCollected());

        hammer.collect(mockMario);
        assertTrue(hammer.isCollected());
    }
}
