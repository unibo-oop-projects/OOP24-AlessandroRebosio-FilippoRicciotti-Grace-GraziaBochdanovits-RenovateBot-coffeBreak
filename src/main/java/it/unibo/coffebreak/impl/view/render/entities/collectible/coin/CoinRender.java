package it.unibo.coffebreak.impl.view.render.entities.collectible.coin;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.view.render.entities.collectible.AbstractCollectableRender;

/**
 * A renderer for the Coin that draws it as a yellow circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class CoinRender extends AbstractCollectableRender {
    private static final String COIN_PATH = "/img/coinSheet.png";
    private static final int COIN_WIDTH = 200;
    private static final int COIN_HEIGHT = 190;
    private final BufferedImage coinSheet;

    /**
     * Constructs a new Coin with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public CoinRender(final Loader resource) {
        super(Objects.requireNonNull(resource, "Resource loader cannot be null"));
        this.coinSheet = resource.loadImage(COIN_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderCollectable(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof Coin) {
            final BufferedImage frame = this.coinSheet.getSubimage(COIN_WIDTH, 0, COIN_WIDTH, COIN_HEIGHT);

            g.drawImage(
                frame,
                (int) entity.getPosition().x(),
                (int) entity.getPosition().y(),
                entity.getDimension().width(),
                entity.getDimension().height(),
                null
            );
        }
    }
}
