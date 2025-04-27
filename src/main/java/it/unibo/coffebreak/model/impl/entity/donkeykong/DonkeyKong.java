package it.unibo.coffebreak.model.impl.entity.donkeykong;

import it.unibo.coffebreak.model.api.entity.donkeykong.BarrelThrowObserver;
import it.unibo.coffebreak.model.api.entity.donkeykong.CommandFactory;
import it.unibo.coffebreak.model.api.entity.donkeykong.Villain;
import it.unibo.coffebreak.model.api.entity.Command;
import it.unibo.coffebreak.model.impl.entity.GameEntity;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Final implementation of the Donkey Kong villain character with thread-safe behavior.
 * 
 * <p>Key features:
 * <ul>
 *   <li>Manages anger levels that affect barrel-throwing frequency</li>
 *   <li>Notifies registered observers when throwing barrels</li>
 *   <li>Provides command objects for action encapsulation</li>
 *   <li>Thread-safe observer management using {@link CopyOnWriteArrayList}</li>
 * </ul>
 * 
 * @see Villain
 * @see GameEntity
 * @see CommandFactory
 * @see AngerManager
 */
public final class DonkeyKong extends GameEntity implements Villain {

    /**
     * Manages anger state and cooldown calculations.
     */
    private final AngerManager angerManager;

    /**
     * Thread-safe list of registered barrel observers.
     */
    private final List<BarrelThrowObserver> observers;

    /**
     * Factory for creating command instances.
     */
    private final CommandFactory commandFactory;

    /**
     * Timestamp of last barrel throw (milliseconds since epoch).
     * Marked volatile for thread visibility.
     */
    private volatile long lastBarrelTime;

    /**
     * Constructs a DonkeyKong instance with required dependencies.
     *
     * @param position the initial position in game coordinates (not null)
     * @param dimension the entity's hitbox dimensions (not null)
     * @param commandFactory factory for creating command instances (not null)
     * @param initialObservers optional list of initial observers (may be null)
     * @throws IllegalArgumentException if position, dimension or commandFactory is null
     */
    public DonkeyKong(final Position position, final Dimension dimension, final CommandFactory commandFactory,
                        final List<BarrelThrowObserver> initialObservers) {
        super(Objects.requireNonNull(position), Objects.requireNonNull(dimension));
        this.commandFactory = Objects.requireNonNull(commandFactory);
        this.angerManager = new AngerManager();
        this.observers = new CopyOnWriteArrayList<>();
        if (initialObservers != null) {
            this.observers.addAll(initialObservers);
        }
        this.lastBarrelTime = 0;
    }

    /**
     * Updates Donkey Kong's state based on elapsed time.
     * Automatically throws barrels when cooldown period expires.
     *
     * @param deltaTime the time elapsed since last update (milliseconds)
     */
    @Override
    public void update(final long deltaTime) {
        final long currentTime = System.currentTimeMillis();
        if (currentTime - lastBarrelTime > angerManager.getCurrentCooldown()) {
            throwBarrel();
            lastBarrelTime = currentTime;
        }
    }

    /**
     * Throws a barrel and notifies all registered observers.
     * Implements {@link Villain#throwBarrel()}.
     */
    @Override
    public void throwBarrel() {
        //observers.forEach(BarrelThrowObserver::onBarrelThrown);
        observers.forEach(observer -> {
            try {
                observer.onBarrelThrown();
            } catch (Exception e) { }
        });
    }

    /**
     * Increases anger level, reducing cooldown between barrel throws.
     * Implements {@link Villain#increaseAnger()}.
     */
    @Override
    public synchronized void increaseAnger() {
        angerManager.increase();
    }

    /**
     * Registers a new barrel observer.
     *
     * @param observer the observer to register (not null)
     * @throws IllegalArgumentException if observer is null
     */
    public void registerObserver(final BarrelThrowObserver observer) {
        observers.add(Objects.requireNonNull(observer, "Observer cannot be null"));
    }

    /**
     * Unregisters a barrel observer.
     *
     * @param observer the observer to remove (not null)
     * @throws IllegalArgumentException if observer is null
     */
    public void removeObserver(final BarrelThrowObserver observer) {
        observers.remove(Objects.requireNonNull(observer, "Observer cannot be null"));
    }

    /**
     * Creates a command for throwing a barrel.
     *
     * @return a new {@link Command} that invokes {@link #throwBarrel()}
     */
    public Command getThrowBarrelCommand() {
        return commandFactory.createThrowCommand(this);
    }

    /**
     * Creates a command for increasing anger level.
     *
     * @return a new {@link Command} that invokes {@link #increaseAnger()}
     */
    public Command getIncreaseAngerCommand() {
        return commandFactory.createAngerCommand(this);
    }

    /**
     * Gets the current anger level (0 = calmest, max = angriest).
     *
     * @return the current anger level (0-based index)
     */
    public synchronized int getAngerLevel() {
        return angerManager.getLevel();
    }

}
