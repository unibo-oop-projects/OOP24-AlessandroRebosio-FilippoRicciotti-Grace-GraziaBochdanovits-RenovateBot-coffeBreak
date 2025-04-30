package it.unibo.coffebreak.model.impl.entity.donkeykong;

import java.util.Objects;

/**
 * Manages the anger level and associated cooldowns for a game character.
 * 
 * <p>This class handles:
 * <ul>
 *   <li>Increasing anger levels</li>
 *   <li>Tracking cooldown periods for each level</li>
 *   <li>Resetting to base level</li>
 *   <li>Providing current state information</li>
 * </ul>
 * }
 */
public final class AngerManager {

    /** Default cooldowns in milliseconds for each anger level. */
    private static final long[] DEFAULT_COOLDOWNS = {4000, 3000, 2000, 1000};
    private final long[] cooldowns;
    private int level;

    /**
     * Creates an AngerManager with default cooldown values.
     */
    public AngerManager() {
        this(DEFAULT_COOLDOWNS);
    }

    /**
     * Creates an AngerManager with custom cooldown values.
     * 
     * @param customCooldowns array of cooldown times in milliseconds (not null, not empty)
     * @throws IllegalArgumentException if customCooldowns is empty
     * @throws NullPointerException if customCooldowns is null
     */
    public AngerManager(final long[] customCooldowns) {
        this.cooldowns = Objects.requireNonNull(customCooldowns, "Cooldowns array cannot be null");
        if (cooldowns.length == 0) {
            throw new IllegalArgumentException("Cooldowns array cannot be empty");
        }
    }

    /**
     * Increases the anger level if not already at maximum.
     * 
     * <p>Each increase typically results in shorter cooldown periods.
     */
    public void increase() {
        if (!isMaxLevel()) {
            level++;
        }
    }

    /**
     * Resets anger level to the minimum (calm state).
     */
    public void reset() {
        level = 0;
    }

    /**
     * Gets the cooldown duration for current anger level.
     * 
     * @return cooldown time in milliseconds
     */
    public long getCurrentCooldown() {
        return cooldowns[level];
    }

    /**
     * Gets the current anger level (0 = calmest).
     * 
     * @return current level index (0-based)
     */
    public int getLevel() {
        return level;
    }

    /**
     * Checks if maximum anger level has been reached.
     * 
     * @return true if at highest anger level, false otherwise
     */
    public boolean isMaxLevel() {
        return level == cooldowns.length - 1;
    }
}
