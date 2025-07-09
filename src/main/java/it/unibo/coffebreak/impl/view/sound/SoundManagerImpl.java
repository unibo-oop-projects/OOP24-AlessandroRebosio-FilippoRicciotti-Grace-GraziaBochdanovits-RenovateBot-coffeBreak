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
public final class SoundManagerImpl implements SoundManager {

    /**
     * Enumeration of every game event that has an associated sound.
     * The relative path is resolved by the {@code ResourceLoader}.
     */
    public enum Event {

        /**
         * enum containing the path to the sound corresponding to the GAME_START.
         */
        GAME_START("/sfx/intro1_long.wav"),
        /**
         * enum containing the path to the sound corresponding to the JUMP.
         */
        JUMP("/sfx/jump.wav"),
        /**
         * enum containing the path to the sound corresponding to WALKING.
         */
        WALKING("/sfx/walking.wav"),
        /**
         * enum containing the path to the sound corresponding to obtaining a POWER_UP.
         */
        POWER_UP("/sfx/hammer.wav"),
        /**
         * enum containing the path to the sound corresponding to obtaining a COIN.
         */
        COIN("/sfx/itemget.wav"),
        /**
         * enum containing the path to the sound corresponding to DEATH.
         */
        DEATH("/sfx/death.wav"),
        /**
         * enum containing the path to the sound corresponding to the LEVEL_CLEAR.
         */
        LEVEL_CLEAR("/sfx/win1.wav"),
        /**
         * enum containing the path to the sound corresponding to the BACKGROUND music.
         */
        BACKGROUND("/sfx/bacmusic.wav");

        private final String path;

        Event(final String path) {
            this.path = path;
        }

        /**
         * @return The relative location of the wav file.
         */
        public String path() {
            return this.path;
        }
    }

    private static final SoundManager INSTANCE = new SoundManagerImpl();

    private final Map<Event, Clip> clips = new EnumMap<>(Event.class);
    private final ResourceLoader loader = new ResourceLoader();

    private SoundManagerImpl() {
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
