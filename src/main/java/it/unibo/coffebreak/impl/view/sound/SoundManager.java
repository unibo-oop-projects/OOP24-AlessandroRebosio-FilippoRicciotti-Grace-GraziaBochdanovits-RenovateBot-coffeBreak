package it.unibo.coffebreak.impl.view.sound;

import java.util.EnumMap;
import java.util.Map;
import javax.sound.sampled.Clip;

import it.unibo.coffebreak.impl.common.ResourceLoader;

/**
 * Centralised audio manager that pre‑loads every sound {@link Clip} once and
 * plays it on demand.
 */
public final class SoundManager {

    /**
     * Enumeration of every game event that has an associated sound.
     * The relative path is resolved by the {@code ResourceLoader}.
     */
    public enum Event {
        GAME_START("/sfx/intro1_long.wav"),
        JUMP("/sfx/jump.wav"),
        WALKING("/sfx/walking.wav"),
        POWER_UP("/sfx/hammer.wav"),
        COIN("/sfx/itemget.wav"),
        DEATH("/sfx/death.wav"),
        LEVEL_CLEAR("/sfx/win1.wav"),
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

    private static final SoundManager INSTANCE = new SoundManager();

    private final Map<Event, Clip> clips = new EnumMap<>(Event.class);
    private final ResourceLoader loader = new ResourceLoader();

    private SoundManager() {
        for (final Event e : Event.values()) {
            final Clip clip = this.loader.loadClip(e.path());
            if (clip != null) {
                this.clips.put(e, clip);
            } else {
                System.err.println("[SoundManager] Missing audio asset: " + e.path());
            }
        }
    }

    /**
     * @return the singleton instance.
     */
    public static SoundManager getInstance() {
        return INSTANCE;
    }

    /**
     * Plays the sound associated with the given event once.
     * If the clip is already running, it is restarted from the beginning.
     */
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
     * Starts looping the clip continuously until {@link #stop(Event)} or
     * {@link #stopAll()} is called.
     */
    public void loop(final Event e) {
        final Clip c = this.clips.get(e);
        if (c != null) {
            if (!c.isRunning()) {
                c.setFramePosition(0);
                c.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    /**
     * Stops the clip if it is currently playing.
     */
    public void stop(final Event e) {
        final Clip c = this.clips.get(e);
        if (c != null && c.isRunning()) {
            c.stop();
        }
    }

    /**
     * Stops every running clip.
     */
    public void stopAll() {
        this.clips.values().forEach(c -> {
            if (c.isRunning()) {
                c.stop();
            }
        });
    }

    /**
     * Releases every {@link Clip} – call this once on application shutdown.
     */
    public void dispose() {
        this.clips.values().forEach(Clip::close);
        this.clips.clear();
    }
}
