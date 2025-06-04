package it.unibo.coffebreak.model.impl.entities.mario.states.normal;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.states.CharacterState;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.entities.structure.Ladder;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;
import it.unibo.coffebreak.model.impl.entities.mario.states.AbstractMarioState;

/**
 * Represents Mario's default state when he is on the ground and can move freely.
 * In this state, Mario can:
 * <ul>
 *   <li>Move horizontally</li>
 *   <li>Jump</li>
 *   <li>Start climbing if he collides with a ladder and presses up/down</li>
 * </ul>
 * 
 * @see CharacterState
 * @see Mario
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class NormalState extends AbstractMarioState {

    private boolean canClimb;

    /**
     * Handles collisions with other entities.
     * 
     * @param character the Mario instance involved in the collision
     * @param other the entity colliding with Mario
     */
    @Override
    public void handleCollision(final Character character, final Entity other) {
        this.canClimb = false;

        if (other instanceof Enemy) {
            character.loseLife();
        }
        if (other instanceof Ladder) {
            this.canClimb = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canClimb() {
        return this.canClimb;
    }
}
