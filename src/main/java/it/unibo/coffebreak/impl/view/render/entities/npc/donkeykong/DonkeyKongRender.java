package it.unibo.coffebreak.impl.view.render.entities.npc.donkeykong;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.impl.view.render.entities.AnimatedEntityRender;

/**
 * A renderer for the Antagonist that draws him as a yellow rectangle on the
 * screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public final class DonkeyKongRender extends AnimatedEntityRender {

    private static final int SPRITE_WIDTH = 48;
    private static final int SPRITE_HEIGHT = 32;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 259;
    private static final int SPACING = 2;

    private static final Map<DKAnimationType, AnimationInfo> ANIMATIONS = Map.ofEntries(
        Map.entry(DKAnimationType.ANGRY, new AnimationInfo(0, 0, 4, SPRITE_WIDTH, SPRITE_HEIGHT, X_OFFSET, Y_OFFSET, SPACING)),
        Map.entry(DKAnimationType.THROW, new AnimationInfo(2, 1, 2, SPRITE_WIDTH, SPRITE_HEIGHT, X_OFFSET, Y_OFFSET, SPACING))
    );

    /**
     * Constructs a new DonkeyKong with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     *  @param loader the loader used to access the sprite sheet
     * @throws NullPointerException if resource is null
     */
    public DonkeyKongRender(final Loader loader) {
        super(Objects.requireNonNull(loader, "Loader cannot be null"));
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, 
                        final int width, final int height) {
        if (!(entity instanceof Antagonist dk)) {
            return;
        }

        final DKAnimationType animation = resolveAnimationType(dk);
        final BufferedImage frame = updateAndGetFrame(dk, animation, ANIMATIONS.get(animation), deltaTime);

        g.drawImage(
            frame,
            (int) dk.getPosition().x(),
            (int) dk.getPosition().y(),
            dk.getDimension().width(),
            dk.getDimension().height(),
            null
        );
    }

    private DKAnimationType resolveAnimationType(final Antagonist dk) {
        return dk.isThrowing() ? DKAnimationType.THROW : DKAnimationType.ANGRY;
    }

    private enum DKAnimationType { ANGRY, THROW }
}
