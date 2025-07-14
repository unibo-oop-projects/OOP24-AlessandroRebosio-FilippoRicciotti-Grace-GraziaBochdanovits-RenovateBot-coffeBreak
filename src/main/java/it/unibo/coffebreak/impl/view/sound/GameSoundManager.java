package it.unibo.coffebreak.impl.view.sound;

import java.util.Objects;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.view.sound.SoundManager;

/**
 * Centralised audio manager that loads and plays sounds {@link Clip} on demand.
 * 
 */
public final class GameSoundManager implements SoundManager {

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
        final Clip c = this.loader.loadClip(e.path());
        if (c == null) {
            return;
        }
        if (c.isRunning()) {
            c.stop();
        }
        c.setFramePosition(0);
        c.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop(final Event e) {
        final Clip c = this.loader.loadClip(e.path());
        if (c != null && !c.isRunning()) {
            if (e == Event.WALKING) {
                setVolume(c, -10.0f); 
            }
            c.setFramePosition(0);
            c.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Event e) {
        final Clip c = this.loader.loadClip(e.path());
        if (c != null && c.isRunning()) {
            c.stop();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopAll() {
        for (final Event e : Event.values()) {
            final Clip c = this.loader.loadClip(e.path());
            if (c != null && c.isRunning()) {
                c.stop();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        for (final Event e : Event.values()) {
            final Clip c = this.loader.loadClip(e.path());
            if (c != null) {
                c.close();
            }
        }
    }

    /**
     * private Method for setting Clips Volume.
     * 
     * @param clip
     * @param decibels
     */
    private void setVolume(final Clip clip, float decibels) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(decibels);
        }
    }
}
