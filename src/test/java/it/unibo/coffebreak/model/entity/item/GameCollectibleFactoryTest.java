package it.unibo.coffebreak.model.entity.item;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.api.entity.item.Collectible;
import it.unibo.coffebreak.model.impl.entity.item.GameCollectibleFactory;
import it.unibo.coffebreak.model.impl.entity.item.ItemType;

class GameCollectibleFactoryTest {

    private static final int X = 10;
    private static final int Y = 20;

    private static final int WIDTH = 30;
    private static final int HEIGTH = 40;

    private GameCollectibleFactory factory;
    private Position testPosition;
    private Dimension testDimension;

    @BeforeEach
    void setUp() {
        factory = new GameCollectibleFactory();
        testPosition = new Position(X, Y);
        testDimension = new Dimension(WIDTH, HEIGTH);
    }

    @Test
    void testCreateHammer() {
        final Collectible hammer = factory.createHammer(testPosition, testDimension);
        assertNotNull(hammer);
        assertEquals(ItemType.HAMMER, hammer.getType());
    }

    @Test
    void testCreateCoin() {
        final Collectible coin = factory.createCoin(testPosition, testDimension);
        assertNotNull(coin);
        assertEquals(ItemType.COIN, coin.getType());
    }

    @Test
    void testCreateBag() {
        final Collectible bag = factory.createBag(testPosition, testDimension);
        assertNotNull(bag);
        assertEquals(ItemType.BAG, bag.getType());
    }

    @Test
    void testCreateHat() {
        final Collectible hat = factory.createHat(testPosition, testDimension);
        assertNotNull(hat);
        assertEquals(ItemType.HAT, hat.getType());
    }

}
