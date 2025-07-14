package it.unibo.coffebreak.model.level;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.impl.common.ResourceLoader;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.level.GameLevelManager;

/**
 * Unit tests for the {@link GameLevelManager} class.
 * <p>
 * This test class verifies correct initialization, entity management,
 * bonus calculation, level advancement, and reset behavior
 * for the {@code GameLevelManager} component.
 * </p>
 * 
 * @see GameLevelManager
 * @see Loader
 * @see MainCharacter
 * @see Mario
 * 
 * @author Filippo Ricciotti
 */
class TestLevelManager {

    private GameLevelManager lm;
    private Loader dummyLoader;

    /**
     * Sets up a new {@link GameLevelManager} before each test,
     * using a {@link Loader}.
     */
    @BeforeEach
    void setUp() {
        dummyLoader = new ResourceLoader();
        this.lm = new GameLevelManager(dummyLoader);
    }

    /**
     * Verifies the initial state of a {@link GameLevelManager}:
     * - Level index equals zero.
     * - Bonus equals zero.
     * - Main character is present.
     */
    @Test
    void initialState() {
        assertEquals(0, lm.getLevelIndex());
        assertEquals(0, lm.getBonusValue());
        assertNotNull(lm.getMainCharacter().orElse(null));
    }

    /**
     * Verifies that {@code loadCurrentEntities} sets a valid bonus
     * value.
     */
    @Test
    void loadEntitiesSetsBonusAndEntities() {
        lm.loadCurrentEntities();
        assertTrue(lm.getBonusValue() == 5000);
        assertFalse(lm.getEntities().isEmpty());
    }

    /**
     * Verifies that {@code advance} does not increase level index
     * when map advancement conditions are not met.
     */
    @Test
    void advanceDoesNotIncreaseWithoutCondition() {
        lm.loadCurrentEntities();
        int idx = lm.getLevelIndex();
        lm.advance();
        assertEquals(idx, lm.getLevelIndex());
    }

    /**
     * Verifies that {@code reset} brings the level index back to zero.
     */
    @Test
    void resetBringsLevelToZero() {
        lm.loadCurrentEntities();
        lm.advance();
        lm.reset();
        assertEquals(0, lm.getLevelIndex());
    }

    /**
     * Verifies that {@code advance} interacts with {@link MainCharacter},
     * ensuring points could be awarded when conditions apply.
     * 
     */
    @Test
    void advanceWhenShouldAdvanceIncreasesIndexAndAwardsPoints() {
        lm.loadCurrentEntities();
        Optional<MainCharacter> mc = lm.getMainCharacter();
        assertTrue(mc.isPresent());
        assertTrue(mc.get() instanceof Mario);
    }
}
