package it.unibo.coffebreak.model.entity;

/**
 * Enumeration representing the different types of collectible items in the
 * game.
 * Each constant represents a specific type of collectible with its base value
 * and behavior when collected by the player.
 * 
 * @author Alessandro Rebosio
 */
public enum ItemType {

    /**
     * The hammer that enables Mario to destroy barrels and eliminate fire enemies.
     * When collected:
     * <ul>
     * <li>Grants temporary destruction capability</li>
     * <li>Allows breaking barrels on contact</li>
     * <li>Can kill fire enemies</li>
     * <li>Typically lasts for a limited time duration</li>
     * </ul>
     * Base value: 100
     */
    HAMMER(100),

    /**
     * Standard currency item that adds to player's score.
     * When collected:
     * <ul>
     * <li>Immediately increases score by 300 points</li>
     * <li>Common collectible throughout levels</li>
     * </ul>
     */
    COIN(300),

    /**
     * Valuable item providing significant score boost.
     * When collected:
     * <ul>
     * <li>Awards 500 points</li>
     * <li>Less common than coins</li>
     * </ul>
     */
    BAG(500),

    /**
     * Valuable item providing significant score boost.
     * When collected:
     * <ul>
     * <li>Awards 500 points</li>
     * <li>Less common than coins</li>
     * </ul>
     */
    HAT(500);

    private final int value;

    /**
     * Constructs an ItemType with the specified base value.
     * 
     * @param value the base point value of this item type
     */
    ItemType(final int value) {
        this.value = value;
    }

    /**
     * Gets the base point value for this item type.
     * 
     * @return the base value in points
     */
    public int getValue() {
        return this.value;
    }
}
