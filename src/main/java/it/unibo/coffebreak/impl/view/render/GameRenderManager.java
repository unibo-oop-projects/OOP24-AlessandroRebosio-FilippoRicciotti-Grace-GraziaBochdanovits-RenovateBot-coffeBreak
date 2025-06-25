package it.unibo.coffebreak.impl.view.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.impl.view.render.entities.enemy.barrel.BarrelRender;
import it.unibo.coffebreak.impl.view.render.entities.enemy.fire.FireRender;
import it.unibo.coffebreak.impl.view.render.entities.mario.MarioRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.ladder.LadderRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.platform.breakable.BreakablePlatformRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.platform.normal.NormalPlatformRender;
import it.unibo.coffebreak.impl.view.render.entities.collectible.coin.CoinRender;
import it.unibo.coffebreak.impl.view.render.entities.collectible.hammer.HammerRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.tank.TankRender;
import it.unibo.coffebreak.impl.view.render.entities.npc.donkeykong.DonkeyKongRender;
import it.unibo.coffebreak.impl.view.render.entities.npc.pauline.PaulineRender;

/**
 * Implementation of {@link RenderManager} that manages the rendering process
 * for both static and dynamic game entities. This class coordinates the drawing
 * of all
 * game elements in the proper order and delegates the actual rendering to
 * specialized renderers for each entity type.
 * 
 * <p>
 * The rendering process follows this sequence:
 * <ol>
 * <li>Clear the screen</li>
 * <li>Render static elements (background, level, etc.)</li>
 * <li>Render dynamic entities in the order they were added</li>
 * </ol>
 * </p>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public final class GameRenderManager implements RenderManager {

    private final Map<Class<? extends Entity>, EntityRender> entityRender = new HashMap<>();
    private final Loader loader;

    /**
     * Constructs a new GameRenderManager with the specified loader.
     * 
     * @param loader the loader used to load resources for entity renders
     */
    public GameRenderManager(final Loader loader) {
        this.loader = loader;

        this.initRender();
    }

    @Override
    public void render(final Graphics2D g, final List<Entity> entities, final int width, final int height,
            final float deltaTime) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        Objects.requireNonNull(entities, "Entities list cannot be null");

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        entities.forEach(entity -> {
            entityRender.entrySet().stream()
                    .filter(entry -> entry.getKey().isInstance(entity))
                    .findFirst()
                    .ifPresent(entry -> entry.getValue().draw(g, entity, deltaTime, width, height));
        });
    }

    private void initRender() {
        this.entityRender.put(Coin.class, new CoinRender(loader));
        this.entityRender.put(Hammer.class, new HammerRender(loader));
        this.entityRender.put(Barrel.class, new BarrelRender(loader));
        this.entityRender.put(Fire.class, new FireRender(loader));
        this.entityRender.put(Mario.class, new MarioRender(loader));
        this.entityRender.put(Pauline.class, new PaulineRender(loader));
        this.entityRender.put(DonkeyKong.class, new DonkeyKongRender(loader));
        this.entityRender.put(Princess.class, new PaulineRender(loader));
        this.entityRender.put(Antagonist.class, new DonkeyKongRender(loader));
        this.entityRender.put(Ladder.class, new LadderRender(loader));
        this.entityRender.put(NormalPlatform.class, new NormalPlatformRender(loader));
        this.entityRender.put(BreakablePlatform.class, new BreakablePlatformRender(loader));
        this.entityRender.put(Tank.class, new TankRender(loader));
    }
}
