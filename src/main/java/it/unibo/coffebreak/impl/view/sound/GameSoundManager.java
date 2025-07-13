package it.unibo.coffebreak.impl.view.sound;

import java.util.EnumMap;
import java.util.Map;
import javax.sound.sampled.Clip;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.view.sound.SoundManager;

/**
 * Centralised audio manager that pre‑loads every sound {@link Clip} once and
 * plays it on demand.
 */
public final class GameSoundManager implements SoundManager {

    private final Map<Event, Clip> clips = new EnumMap<>(Event.class);

    /**
     * Creates a new sound manager and loads all audio clips via the given loader.
     * 
     * @param loader the resource loader to load sound assets
     */
    public GameSoundManager(final Loader loader) {
        for (final Event e : Event.values()) {
            final Clip clip = loader.loadClip(e.path());

            this.clips.put(e, clip);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Event e) {
        final Clip c = this.clips.get(e);
        if (c == null) {
            return; // asset missing
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
        final Clip c = this.clips.get(e);
        if (c != null && !c.isRunning()) {
            c.setFramePosition(0);
            c.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Event e) {
        final Clip c = this.clips.get(e);
        if (c != null && c.isRunning()) {
            c.stop();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopAll() {
        this.clips.values().forEach(c -> {
            if (c.isRunning()) {
                c.stop();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        this.clips.values().forEach(Clip::close);
        this.clips.clear();
    }

}
