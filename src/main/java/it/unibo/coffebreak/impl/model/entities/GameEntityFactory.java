package it.unibo.coffebreak.impl.model.entities;

import it.unibo.coffebreak.api.model.entities.EntityFactory;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.BoundingBox2D;
import it.unibo.coffebreak.impl.common.Position2D;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.enemy.barrel.GameBarrel;
import it.unibo.coffebreak.impl.model.entities.enemy.fire.GameFire;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.mario.score.GameScore;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.impl.model.entities.structure.tank.GameTank;

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

    /**
     * Deafult entity bounding box dimension.
     */
    public static final BoundingBox2D DEFAULT_BOUNDINGBOX = new BoundingBox2D(25, 25);

    private final Score score = new GameScore();

    /**
     * {@inheritDoc}
     */
    @Override
    public Coin createCoin(final Position2D position) {
        return new Coin(position, DEFAULT_BOUNDINGBOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hammer createHammer(final Position2D position) {
        return new Hammer(position, DEFAULT_BOUNDINGBOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Barrel createBarrel(final Position2D position) {
        // TODO: The first barrel must be able to transform, the others must be randomly
        // (RICCIOTTI)
        return new GameBarrel(position, DEFAULT_BOUNDINGBOX, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fire createFire(final Position2D position) {
        return new GameFire(position, DEFAULT_BOUNDINGBOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Antagonist createDonkeyKong(final Position2D position) {
        // TODO: add a boolean parameter mustThrow to the method and use it in the
        // DonkeyKong constructor
        return new DonkeyKong(position, DEFAULT_BOUNDINGBOX.mul(2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Princess createPrincess(final Position2D position) {
        return new Pauline(position, DEFAULT_BOUNDINGBOX.mulY(2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ladder createNormalLadder(final Position2D position) {
        return new NormalLadder(position, DEFAULT_BOUNDINGBOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Platform createNormalPlatform(final Position2D position) {
        return new NormalPlatform(position, DEFAULT_BOUNDINGBOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Platform createBreakablePlatform(final Position2D position) {
        return new BreakablePlatform(position, DEFAULT_BOUNDINGBOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tank createTank(final Position2D position) {
        return new GameTank(position, DEFAULT_BOUNDINGBOX.mulY(2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MainCharacter createMario(final Position2D position) {
        return new Mario(position, DEFAULT_BOUNDINGBOX.mulY(2), score);
    }
}
