package it.unibo.coffebreak.impl.view.sound;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.view.sound.SoundManager;

/**
 * Centralised audio manager that loads and plays sounds {@link Clip} on demand.
 * 
 * @author Filippo Ricciotti
 */
public final class GameSoundManager implements SoundManager {

    private static final float LOWER_WALKING = -10.0f;
    private final Loader loader;

    /**
     * Creates a new sound manager that saves the given loader.
     * 
     * @param loader the resource loader to load sound assets
     */
    public GameSoundManager(final Loader loader) {
        this.loader = Objects.requireNonNull(loader, "The loader cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Event e) {
        this.withClip(e, Clip::isRunning, Clip::stop);
        this.withClip(e, c -> true, c -> {
            c.setFramePosition(0);
            c.start();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop(final Event e) {
        this.withClip(e, c -> e.equals(Event.WALKING), c -> this.setVolume(c, LOWER_WALKING));
        this.withClip(e, c -> !c.isRunning(), c -> {
            c.setFramePosition(0);
            c.loop(Clip.LOOP_CONTINUOUSLY);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Event e) {
        this.withClip(e, Clip::isRunning, Clip::stop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopAll() {
        Arrays.stream(Event.values())
                .forEach(this::stop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        Arrays.stream(Event.values())
                .forEach(e -> withClip(e, c -> true, Clip::close));
    }

    /**
     * Helper method to perform an action on a Clip if present and condition is met.
     *
     * @param e         the event whose clip should be loaded
     * @param condition predicate to check if the action should be performed on the
     *                  clip
     * @param action    the action to perform on the clip if present and condition
     *                  is met
     */
    private void withClip(final Event e, final java.util.function.Predicate<Clip> condition,
            final Consumer<Clip> action) {
        Optional.ofNullable(this.loader.loadClip(e.path()))
                .filter(condition)
                .ifPresent(action);
    }

    /**
     * private Method for setting Clips Volume.
     * 
     * @param clip
     * @param decibels
     */
    private void setVolume(final Clip clip, final float decibels) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(decibels);
        }
    }
}
