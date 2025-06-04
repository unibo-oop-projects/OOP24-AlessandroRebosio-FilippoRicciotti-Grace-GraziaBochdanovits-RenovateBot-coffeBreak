package it.unibo.coffebreak.model.impl;

import it.unibo.coffebreak.model.api.EntityFactory;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.collectible.coin.Coin;
import it.unibo.coffebreak.model.impl.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.model.impl.entities.enemy.barrel.GameBarrel;
import it.unibo.coffebreak.model.impl.entities.enemy.fire.GameFire;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;
import it.unibo.coffebreak.model.impl.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.model.impl.entities.npc.princess.Princess;
import it.unibo.coffebreak.model.impl.entities.structure.ladder.normal.NormalLadder;
import it.unibo.coffebreak.model.impl.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.model.impl.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.model.impl.entities.structure.tank.GameTank;

/**
 * {@code GameEntityFactory} is the implementation of the
 * <b>{@link EntityFactory} interface.</b>
 * <p>
 * It provides the logic for instantiating
 * all the different types of game entities
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class GameEntityFactory implements EntityFactory {
    private static final BoundingBox2D DEF_BOX = new BoundingBox2D(32, 32);

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createCoin(final Position2D position) {
        return new Coin(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createHammer(final Position2D position) {
        return new Hammer(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Barrel createBarrel(final Position2D position) {
        return new GameBarrel(position, DEF_BOX, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createFire(final Position2D position) {
        return new GameFire(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createDonkeyKong(final Position2D position) {
        return new DonkeyKong(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createPrincess(final Position2D position) {
        return new Princess(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createLadder(final Position2D position) {
        return new NormalLadder(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createPlatform(final Position2D position) {
        return new NormalPlatform(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createBreakablePlatform(final Position2D position) {
        return new BreakablePlatform(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createTank(final Position2D position) {
        return new GameTank(position, DEF_BOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity createMario(final Position2D position) {
        return new Mario(position, DEF_BOX);
    }

}
