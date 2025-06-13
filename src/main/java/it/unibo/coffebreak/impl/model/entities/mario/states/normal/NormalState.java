package it.unibo.coffebreak.impl.model.entities.mario.states.normal;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
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

    private static final float TOLLERANCE = 0.1f;
    private boolean canClimb;
    private boolean isClimbing;

    /**
     * Handles collisions with other entities.
     * 
     * @param character the Mario instance involved in the collision
     * @param other     the entity colliding with Mario
     */
    @Override
    public void handleCollision(final MainCharacter character, final Entity other) {

        switch (other) {
            case final Enemy enemy -> character.loseLife();
            case final Ladder ladder -> handleLadderCollision(character, ladder);
            case final Platform platform -> this.canClimb = false; 
            //TODO: in questo modo però non potrà mai scendere dunque sistemare
            default -> { }
        }
    }

    private void handleLadderCollision(final MainCharacter character, final Ladder ladder) {
        final float marioCenter = character.getPosition().x() + character.getDimension().width() * 0.5f;
        final float ladderCenter = ladder.getPosition().x() + ladder.getDimension().width() * 0.5f;
        this.canClimb = Math.abs(marioCenter - ladderCenter) <= character.getDimension().width() * TOLLERANCE;

        if (canClimb) {
            this.isClimbing = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isClimbing() {
        return this.isClimbing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopClimbing() {
        this.isClimbing = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canClimb() {
        return this.canClimb;
    }
}
