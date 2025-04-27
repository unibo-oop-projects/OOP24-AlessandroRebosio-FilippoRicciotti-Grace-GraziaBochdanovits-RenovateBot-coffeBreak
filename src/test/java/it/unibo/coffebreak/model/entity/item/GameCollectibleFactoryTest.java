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

/**
 * Test class for {@link GameCollectibleFactory}.
 * <p>
 * Verifies the correct creation of different types of collectible items
 * with their expected properties and behaviors.
 * </p>
 * 
 * <p>
 * The tests cover:
 * <ul>
 * <li>Creation of all supported item types (hammer, coin, bag, hat)</li>
 * <li>Verification of correct item type assignment</li>
 * <li>Basic property validation</li>
 * </ul>
 * </p>
 * 
 * @see GameCollectibleFactory
 * @see Collectible
 * @see ItemType
 * 
 * @author Alessandro Rebosio
 */
class GameCollectibleFactoryTest {

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

    /**
     * Initializes test fixtures before each test method.
     * <p>
     * Creates:
     * <ul>
     * <li>A new {@link GameCollectibleFactory} instance</li>
     * <li>A standard test position</li>
     * <li>A standard test dimension</li>
     * </ul>
     * </p>
     */
    @BeforeEach
    void setUp() {
        factory = new GameCollectibleFactory();
        testPosition = new Position(X, Y);
        testDimension = new Dimension(WIDTH, HEIGTH);
    }

    /**
     * Tests the creation of a hammer item.
     * <p>
     * Verifies that:
     * <ul>
     * <li>The returned object is not null</li>
     * <li>The item has the correct {@link ItemType#HAMMER} type</li>
     * </ul>
     * </p>
     */
    @Test
    void testCreateHammer() {
        final Collectible hammer = factory.createHammer(testPosition, testDimension);
        assertNotNull(hammer);
        assertEquals(ItemType.HAMMER, hammer.getType());
    }

    /**
     * Tests the creation of a coin item.
     * <p>
     * Verifies that:
     * <ul>
     * <li>The returned object is not null</li>
     * <li>The item has the correct {@link ItemType#COIN} type</li>
     * </ul>
     * </p>
     */
    @Test
    void testCreateCoin() {
        final Collectible coin = factory.createCoin(testPosition, testDimension);
        assertNotNull(coin);
        assertEquals(ItemType.COIN, coin.getType());
    }

    /**
     * Tests the creation of a bag item.
     * <p>
     * Verifies that:
     * <ul>
     * <li>The returned object is not null</li>
     * <li>The item has the correct {@link ItemType#BAG} type</li>
     * </ul>
     * </p>
     */
    @Test
    void testCreateBag() {
        final Collectible bag = factory.createBag(testPosition, testDimension);
        assertNotNull(bag);
        assertEquals(ItemType.BAG, bag.getType());
    }

    /**
     * Tests the creation of a hat item.
     * <p>
     * Verifies that:
     * <ul>
     * <li>The returned object is not null</li>
     * <li>The item has the correct {@link ItemType#HAT} type</li>
     * </ul>
     * </p>
     */
    @Test
    void testCreateHat() {
        final Collectible hat = factory.createHat(testPosition, testDimension);
        assertNotNull(hat);
        assertEquals(ItemType.HAT, hat.getType());
    }
}
