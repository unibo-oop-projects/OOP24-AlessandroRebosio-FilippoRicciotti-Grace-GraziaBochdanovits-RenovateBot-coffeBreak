package it.unibo.coffebreak.model.impl.entity;

import it.unibo.coffebreak.model.api.entity.Movable;
import it.unibo.coffebreak.model.impl.entity.enemy.BarrelMovementStrategy;
import it.unibo.coffebreak.model.impl.entity.enemy.FireMovementStrategy;

/**
 * Enumerates the different types of enemies in the game.
 * <p>
 * Each enemy type provides its own movement strategy implementation through
 * the {@link #createMovementStrategy()} method.
 * </p>
 */
public enum EnemyType {
   /**
    * Represents a barrel enemy type.
    */
   BARREL {
      @Override
      public Movable createMovementStrategy() {
         return new BarrelMovementStrategy();
      }
   },

   /**
    * Represents a fire enemy type.
    */
   FIRE {
      @Override
      public Movable createMovementStrategy() {
         return new FireMovementStrategy();
      }
   };

   /**
     * Creates a new movement strategy instance appropriate for this enemy type.
     * 
     * @return a new movement strategy instance (never {@code null})
     */
   public abstract Movable createMovementStrategy();
}
