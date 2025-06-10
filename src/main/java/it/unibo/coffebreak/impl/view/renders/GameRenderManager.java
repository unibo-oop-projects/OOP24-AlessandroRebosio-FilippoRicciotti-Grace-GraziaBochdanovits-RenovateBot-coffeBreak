package it.unibo.coffebreak.impl.view.renders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.api.view.render.RenderManager;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.impl.model.entities.structure.tank.GameTank;
import it.unibo.coffebreak.impl.view.renders.entities.collectible.coin.CoinRender;
import it.unibo.coffebreak.impl.view.renders.entities.collectible.hammer.HammerRender;
import it.unibo.coffebreak.impl.view.renders.entities.enemy.barrel.BarrelRender;
import it.unibo.coffebreak.impl.view.renders.entities.enemy.fire.FireRender;
import it.unibo.coffebreak.impl.view.renders.entities.mario.PlayerRender;
import it.unibo.coffebreak.impl.view.renders.entities.npc.antagonist.DonkeyKongrender;
import it.unibo.coffebreak.impl.view.renders.entities.npc.pauline.PaulineRender;
import it.unibo.coffebreak.impl.view.renders.entities.structure.ladder.LadderRender;
import it.unibo.coffebreak.impl.view.renders.entities.structure.platform.PlatformRender;
import it.unibo.coffebreak.impl.view.renders.entities.structure.tank.TankRender;

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
                    .ifPresent(entry -> entry.getValue().draw(g, entity, deltaTime));
        });
    }

    private void initRender() {
        this.entityRender.put(Coin.class, new CoinRender(loader));
        this.entityRender.put(Hammer.class, new HammerRender(loader));
        this.entityRender.put(Barrel.class, new BarrelRender(loader));
        this.entityRender.put(Fire.class, new FireRender(loader));
        this.entityRender.put(Mario.class, new PlayerRender(loader));
        this.entityRender.put(Pauline.class, new PaulineRender(loader));
        this.entityRender.put(DonkeyKong.class, new DonkeyKongrender(loader));
        this.entityRender.put(Ladder.class, new LadderRender(loader));
        this.entityRender.put(NormalPlatform.class, new PlatformRender(loader));
        this.entityRender.put(GameTank.class, new TankRender(loader));
    }
}
