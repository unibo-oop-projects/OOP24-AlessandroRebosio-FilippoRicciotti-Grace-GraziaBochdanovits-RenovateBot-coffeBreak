package it.unibo.coffebreak.impl.view.render.entities.mario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;
import it.unibo.coffebreak.impl.view.render.entities.AnimatedEntityRender;

/**
 * Render implementation for {@link Mario} player entity.
 * Handles all animation states and rendering logic for the Mario character.
 *
 * @author Grazia Bochdanovits de Kavna
 */
public final class MarioRender extends AnimatedEntityRender<MarioRender.MarioAnimationType> {

    private static final int SPRITE_SIZE = 16;
    private static final int DOUBLE_SIZE = SPRITE_SIZE * 2;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 1;
    private static final int X_WALK = 19;
    private static final int X_CLIMB = 91;
    private static final int X_JUMP = 55;
    private static final int Y_HAMMER = 73;
    private static final int SPACING = 2;

    /** 
     * Mapping of animation types to their sprite sheet information.
     */
    private static final Map<MarioAnimationType, AnimationInfo> ANIMATIONS = Map.ofEntries(
        Map.entry(MarioAnimationType.IDLE,   new AnimationInfo(1, SPRITE_SIZE, SPRITE_SIZE, X_OFFSET, Y_OFFSET, SPACING, 0.2f)),
        Map.entry(MarioAnimationType.WALK,   new AnimationInfo(2, SPRITE_SIZE, SPRITE_SIZE, X_WALK, Y_OFFSET, SPACING, 0.2f)),
        Map.entry(MarioAnimationType.CLIMB,  new AnimationInfo(2, SPRITE_SIZE, SPRITE_SIZE, X_CLIMB, Y_OFFSET, SPACING, 0.2f)),
        Map.entry(MarioAnimationType.JUMP,   new AnimationInfo(1, SPRITE_SIZE, SPRITE_SIZE, X_JUMP, Y_OFFSET, SPACING, 0.2f)),
        Map.entry(MarioAnimationType.HAMMER, new AnimationInfo(6, DOUBLE_SIZE, DOUBLE_SIZE, X_OFFSET, Y_HAMMER, SPACING, 0.2f))
    );

    /**
     * Constructs a new MarioRender.
     *
     * @param loader the loader used to access the sprite sheet
     * @throws NullPointerException if resource is null
     */
    public MarioRender(final Loader loader) {
        super(Objects.requireNonNull(loader, "Loader cannot be null"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime,
                        final int width, final int height) {
        if (!(entity instanceof Mario mario)) {
            return;
        }

        final MarioAnimationType animation = resolveAnimationType(mario);
        final BufferedImage frame = getMarioFrame(mario, animation, deltaTime);

        g.drawImage(
            frame,
            (int) mario.getPosition().x(),
            (int) mario.getPosition().y(),
            mario.getDimension().width(),
            mario.getDimension().height(),
            null
        );
    }

    private BufferedImage getMarioFrame(final MainCharacter mario, final MarioAnimationType animation, final float deltaTime) {
        final BufferedImage frame = updateAndGetFrame(mario, animation, ANIMATIONS.get(animation), deltaTime);
        return mario.isFacingRight() ? frame : flipImageHorizontally(frame);
    }

    /**
     * Determines the appropriate animation type based on Mario's current state.
     *
     * @param mario the Mario character to evaluate
     * @return the appropriate MarioAnimationType for the current state
     */
    private MarioAnimationType resolveAnimationType(final MainCharacter mario) {
        final List<Map.Entry<Predicate<MainCharacter>, MarioAnimationType>> conditions = List.of(
            Map.entry(m -> m.isClimbing(), MarioAnimationType.CLIMB),
            Map.entry(MainCharacter::isJumping, MarioAnimationType.JUMP),
            Map.entry(m -> m.getCurrentState() instanceof WithHammerState, MarioAnimationType.HAMMER),
            Map.entry(m -> Math.abs(m.getVelocity().x()) > 0.0f, MarioAnimationType.WALK)
        );

        return conditions.stream()
            .filter(entry -> entry.getKey().test(mario))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(MarioAnimationType.IDLE);
    }

    /**
     * Enumeration of possible Mario animation types.
     */
    protected enum MarioAnimationType {
        /** Character is standing still. */
        IDLE,
        /** Character is walking. */
        WALK,
        /** Character is climbing. */
        CLIMB,
        /** Character is jumping. */
        JUMP,
        /** Character is using a hammer. */
        HAMMER
    }
}
