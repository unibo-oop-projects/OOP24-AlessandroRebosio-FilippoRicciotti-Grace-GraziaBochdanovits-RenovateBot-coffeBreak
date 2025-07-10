package it.unibo.coffebreak.impl.view.sound;

import java.util.EnumMap;
import java.util.Map;
import javax.sound.sampled.Clip;

import it.unibo.coffebreak.api.view.sound.SoundManager;
import it.unibo.coffebreak.impl.common.ResourceLoader;

/**
 * Centralised audio manager that pre‑loads every sound {@link Clip} once and
 * plays it on demand.
 */
public final class GameSoundManager implements SoundManager {



    private static final SoundManager INSTANCE = new GameSoundManager();

    private final Map<Event, Clip> clips = new EnumMap<>(Event.class);
    private final ResourceLoader loader = new ResourceLoader();

    private GameSoundManager() {
        for (final Event e : Event.values()) {
            final Clip clip = this.loader.loadClip(e.path());

            this.clips.put(e, clip);
        }
    }

    /**
     * @return the singleton instance.
     */
    public static SoundManager getInstance() {
        return INSTANCE;
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
