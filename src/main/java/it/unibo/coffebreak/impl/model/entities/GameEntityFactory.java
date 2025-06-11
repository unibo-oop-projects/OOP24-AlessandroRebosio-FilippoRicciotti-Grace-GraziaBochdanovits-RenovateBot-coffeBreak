package it.unibo.coffebreak.impl.model.entities;

import it.unibo.coffebreak.api.model.entities.EntityFactory;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
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
import it.unibo.coffebreak.impl.model.entities.mario.lives.GameLivesManager;
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
    public static final Dimension DEFAULT_BOUNDINGBOX = new Dimension(25, 25);

    private final Score score = new GameScore();
    private final LivesManager livesManager = new GameLivesManager();

    /**
     * {@inheritDoc}
     */
    @Override
    public Coin createCoin(final Position position) {
        return new Coin(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hammer createHammer(final Position position) {
        return new Hammer(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Barrel createBarrel(final Position position) {
        // TODO: The first barrel must be able to transform, the others must be randomly
        // (RICCIOTTI)
        return new GameBarrel(position, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fire createFire(final Position position) {
        return new GameFire(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Antagonist createDonkeyKong(final Position position) {
        return new DonkeyKong(position, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Princess createPrincess(final Position position) {
        return new Pauline(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ladder createNormalLadder(final Position position) {
        return new NormalLadder(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Platform createNormalPlatform(final Position position) {
        return new NormalPlatform(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Platform createBreakablePlatform(final Position position) {
        return new BreakablePlatform(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tank createTank(final Position position) {
        return new GameTank(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MainCharacter createMario(final Position position) {
        return new Mario(position, score, livesManager);
    }
}
