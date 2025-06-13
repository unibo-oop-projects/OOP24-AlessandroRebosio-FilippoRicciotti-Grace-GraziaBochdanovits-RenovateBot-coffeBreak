package it.unibo.coffebreak.impl.model.entities;

import it.unibo.coffebreak.api.model.entities.EntityFactory;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.enemy.barrel.GameBarrel;
import it.unibo.coffebreak.impl.model.entities.enemy.fire.GameFire;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.impl.model.entities.structure.tank.GameTank;

/**
 * {@code GameEntityFactory} is the implementation of the {@link EntityFactory}
 * interface.
 * <p>
 * Provides the logic for instantiating all the different types of game
 * entities.
 * </p>
 *
 * All factory methods require both a {@code Position} and a {@code Dimension}
 * parameter.
 *
 * @author Filippo Ricciotti
 */
public class GameEntityFactory implements EntityFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Coin createCoin(final Position position) {
        return new Coin(position, new Dimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hammer createHammer(final Position position) {
        return new Hammer(position, new Dimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Barrel createBarrel(final Position position) {
        return new GameBarrel(position, new Dimension(), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fire createFire(final Position position) {
        return new GameFire(position, new Dimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Antagonist createDonkeyKong(final Position position, final Dimension dimension) {
        return new DonkeyKong(position, dimension, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Princess createPrincess(final Position position, final Dimension dimension) {
        return new Pauline(position, dimension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ladder createNormalLadder(final Position position) {
        return new NormalLadder(position, new Dimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Platform createNormalPlatform(final Position position) {
        return new NormalPlatform(position, new Dimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Platform createBreakablePlatform(final Position position) {
        return new BreakablePlatform(position, new Dimension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tank createTank(final Position position, final Dimension dimension) {
        return new GameTank(position, dimension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MainCharacter createMario(final Position position, final Dimension dimension) {
        return new Mario(position, dimension);
    }
}
