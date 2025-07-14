package it.unibo.coffebreak.model.level.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.enemy.fire.GameFire;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.impl.model.level.entity.GameEntityManager;

/**
 * Unit tests for the {@link GameEntityManager} class.
 * <p>
 * These tests verify entity management functionalities such as:
 * adding entities, retrieving entities, accessing the main character,
 * and accessing grid dimensions.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
class TestEntityManager {

    private GameEntityManager entityManager;

    /**
     * Initializes a new {@link GameEntityManager} before each test.
     */
    @BeforeEach
    void setUp() {
        this.entityManager = new GameEntityManager();
    }

    /**
     * Verifies that a new {@link GameEntityManager} contains no entities except the
     * MainCharacter.
     */
    @Test
    void initialStateShouldBeEmpty() {
        assertTrue(entityManager.getEntities().isEmpty());
        assertTrue(entityManager.getMainCharacter().isPresent());
    }

    /**
     * Verifies that adding an entity successfully registers it in the manager.
     */
    @Test
    void shouldAddAndRetrieveEntities() {
        final List<String> map = List.of("RP!MDFTHCL");

        entityManager.loadEntities(map, false);
        final List<Entity> entities = entityManager.getEntities();
        assertTrue(entities.stream().anyMatch(e -> e instanceof Pauline));
        assertTrue(entities.stream().anyMatch(e -> e instanceof NormalPlatform));
        assertTrue(entities.stream().anyMatch(e -> e instanceof BreakablePlatform));
        assertTrue(entities.stream().anyMatch(e -> e instanceof Mario));
        assertTrue(entities.stream().anyMatch(e -> e instanceof DonkeyKong));
        assertTrue(entities.stream().anyMatch(e -> e instanceof GameFire));
        assertTrue(entities.stream().anyMatch(e -> e instanceof Tank));
        assertTrue(entities.stream().anyMatch(e -> e instanceof Hammer));
        assertTrue(entities.stream().anyMatch(e -> e instanceof Coin));
        assertTrue(entities.stream().anyMatch(e -> e instanceof NormalLadder));
    }

    /**
     * Verifies that {@link GameEntityManager#getMainCharacter()} returns
     * a {@link Mario} instance.
     */
    @Test
    void shouldRetrieveMainCharacterIfPresent() {
        final Optional<? extends Entity> mainCharacter = entityManager.getMainCharacter();
        assertTrue(mainCharacter.isPresent());
        assertTrue(mainCharacter.get() instanceof Mario);
    }

}
