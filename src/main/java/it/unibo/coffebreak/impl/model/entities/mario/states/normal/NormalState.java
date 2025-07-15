package it.unibo.coffebreak.impl.model.entities.mario.states.normal;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.mario.states.AbstractMarioState;

/**
 * Represents Mario's default state when he is on the ground and can move
 * freely.
 * In this state, Mario can:
 * <ul>
 * <li>Move horizontally</li>
 * <li>Jump</li>
 * <li>Start climbing if he collides with a ladder and presses up/down</li>
 * </ul>
 * 
 * @see CharacterState
 * @see Mario
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class NormalState extends AbstractMarioState {

    private static final float INVINCIBILITY_TIME = 2.0f;

    private boolean canClimb;
    private float invincibilityTimer;

    /**
     * Updates the normal state for each frame.
     * Resets the climbing flag to false at the beginning of each update cycle.
     * This ensures that climbing is only possible when actively colliding with a ladder.
     * Also manages the invincibility timer to prevent rapid life loss.
     * 
     * @param character the main character (Mario) to update
     * @param deltaTime the time elapsed since the last update in seconds
     */
    @Override
    public void update(final MainCharacter character, final float deltaTime) {
        this.canClimb = false;

        if (this.invincibilityTimer > 0) {
            this.invincibilityTimer -= deltaTime;
        }
    }

    /**
     * Handles collisions with other entities.
     * Includes invincibility timer to prevent rapid life loss from multiple
     * collisions.
     * 
     * @param character the Mario instance involved in the collision
     * @param other     the entity colliding with Mario
     */
    @Override
    public void handleCollision(final MainCharacter character, final Entity other) {
        switch (other) {
            case final Enemy enemy -> {
                if (this.invincibilityTimer <= 0) {
                    character.loseLife();
                    this.invincibilityTimer = INVINCIBILITY_TIME; 
                }
            }
            case final Ladder ladder -> this.canClimb = true;
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canClimb() {
        return this.canClimb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canJump() {
        return true;
    }
}
