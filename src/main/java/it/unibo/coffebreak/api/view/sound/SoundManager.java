package it.unibo.coffebreak.api.view.sound;

import javax.sound.sampled.Clip;


/**
 * Contract for any audio system used by the game.
 * Different implementations (real, mock, or platform‑specific)
 * can be swapped in without toccare il resto del codice.
 */
public interface SoundManager {

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

    /**
     * Plays the sound associated with the given event once.
     * If the clip is already running, it is restarted from the beginning.
     * 
     * @param e relative event to the sound we want the SoundManager to start
     *          playing.
     */
    void play(Event e);

    /**
     * Starts looping the clip continuously until {@link #stop(Event)} or
     * {@link #stopAll()} is called.
     * 
     * @param e relative event to the sound we want the SoundManager to start
     *          playing in loop.
     */
    void loop(Event e);

    /**
     * Stops the clip if it is currently playing.
     * 
     * @param e relative event to the sound we want the SoundManager to stop
     *          playing.
     */
    void stop(Event e);

    /**
     * Stops every running clip.
     */
    void stopAll();

    /**
     * Releases every {@link Clip} – call this once on application shutdown.
     */
    void dispose();
}
