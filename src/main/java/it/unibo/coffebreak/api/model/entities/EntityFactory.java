package it.unibo.coffebreak.api.model.entities;

import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;

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
    Collectible createCoin(Position position);

    /**
     * Factory Method to create a new {@link Hammer}.
     * 
     * @param position
     * @return Hammer Entity
     */
    Collectible createHammer(Position position);

    /**
     * Factory Method to create a new {@link Barrel}.
     * 
     * @param position
     * @return Barrel Entity
     */
    Barrel createBarrel(Position position);

    /**
     * Factory Method to create a new {@link Fire}.
     * 
     * @param position
     * @return Fire Entity
     */
    Fire createFire(Position position);

    /**
     * Factory Method to create a new {@link DonkeyKong}.
     * 
     * @param position
     * @return Donkey Kong Entity
     */
    Antagonist createDonkeyKong(Position position);

    /**
     * Factory Method to create a new {@link Pauline}.
     * 
     * @param position
     * @return Princess Entity
     */
    Princess createPrincess(Position position);

    /**
     * Factory Method to create a new {@link Ladder}.
     * 
     * @param position
     * @return Ladder Entity
     */
    Ladder createNormalLadder(Position position);

    /**
     * Factory Method to create a new {@link Platform}.
     * 
     * @param position
     * @return Platform Entity
     */
    Platform createNormalPlatform(Position position);

    /**
     * Factory Method to create a new {@link BreakablePlatform}.
     * 
     * @param position
     * @return Breakable Platform Entity
     */
    Platform createBreakablePlatform(Position position);

    /**
     * Factory Method to create a new {@link Tank}.
     * 
     * @param position
     * @return Tank Entity
     */
    Tank createTank(Position position);

    /**
     * Factory Method to create a new {@link Mario}.
     * 
     * @param position
     * @return Mario Entity
     */
    MainCharacter createMario(Position position);
}
