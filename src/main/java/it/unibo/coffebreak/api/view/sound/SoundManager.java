package it.unibo.coffebreak.api.view.sound;

import javax.sound.sampled.Clip;

import it.unibo.coffebreak.impl.view.sound.GameSoundManager.Event;

/**
 * Contract for any audio system used by the game.
 * Different implementations (real, mock, or platform‑specific)
 * can be swapped in without toccare il resto del codice.
 */
public interface SoundManager {

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
