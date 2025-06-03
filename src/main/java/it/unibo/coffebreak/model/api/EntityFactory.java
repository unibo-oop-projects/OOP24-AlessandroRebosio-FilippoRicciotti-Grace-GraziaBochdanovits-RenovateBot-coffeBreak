package it.unibo.coffebreak.model.api;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.enemy.fire.Fire;
import it.unibo.coffebreak.model.api.entities.structure.Ladder;
import it.unibo.coffebreak.model.api.entities.structure.Platform;
import it.unibo.coffebreak.model.api.entities.structure.Tank;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;
import it.unibo.coffebreak.model.impl.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.model.impl.entities.npc.princess.Princess;
import it.unibo.coffebreak.model.impl.entities.structure.platform.breakable.BreakablePlatform;

/**
 * The {@code EntityFactory} interface provides a set of factory methods
 * to create various types of game entities used in the Level Manager.
 * <p>
 * Each method returns a new instance of a specific {@link Entity } type
 * positioned
 * at the given coordinates.
 * </p>
 *
 * @author Filippo Ricciotti
 */
public interface EntityFactory {

    /**
     * Factory Method to create a new Coin {@link Entity}.
     * 
     * @param position
     * @return Coin Entity
     */
    Entity createCoin(Position2D position);

    /**
     * Factory Method to create a new {@link Hammer}.
     * 
     * @param position
     * @return Hammer Entity
     */
    Entity createHammer(Position2D position);

    /**
     * Factory Method to create a new {@link Barrel}.
     * 
     * @param position
     * @return Barrel Entity
     */
    Entity createBarrel(Position2D position);

    /**
     * Factory Method to create a new {@link Fire}.
     * 
     * @param position
     * @return Fire Entity
     */
    Entity createFire(Position2D position);

    /**
     * Factory Method to create a new {@link DonkeyKong}.
     * 
     * @param position
     * @return Donkey Kong Entity
     */
    Entity createDonkeyKong(Position2D position);

    /**
     * Factory Method to create a new {@link Princess}.
     * 
     * @param position
     * @return Princess Entity
     */
    Entity createPrincess(Position2D position);

    /**
     * Factory Method to create a new {@link Ladder}.
     * 
     * @param position
     * @return Ladder Entity
     */
    Entity createLadder(Position2D position);

    /**
     * Factory Method to create a new {@link Platform}.
     * 
     * @param position
     * @return Platform Entity
     */
    Entity createPlatform(Position2D position);

    /**
     * Factory Method to create a new {@link BreakablePlatform}.
     * 
     * @param position
     * @return Breakable Platform Entity
     */
    Entity createBreakablePlatform(Position2D position);

    /**
     * Factory Method to create a new {@link Tank}.
     * 
     * @param position
     * @return Tank Entity
     */
    Entity createTank(Position2D position);

    /**
     * Factory Method to create a new {@link Mario}.
     * 
     * @param position
     * @return Mario Entity
     */
    Entity createMario(Position2D position);
}
