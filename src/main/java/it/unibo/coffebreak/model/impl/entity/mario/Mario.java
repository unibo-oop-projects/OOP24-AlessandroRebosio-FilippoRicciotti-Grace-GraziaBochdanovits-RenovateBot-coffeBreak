package it.unibo.coffebreak.model.impl.entity.mario;

import it.unibo.coffebreak.model.api.entity.Entity;
import it.unibo.coffebreak.model.api.entity.mario.Character;
import it.unibo.coffebreak.model.impl.entity.GameEntity;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

public class Mario extends GameEntity implements Character {

    public Mario(Position position, Dimension dimension) {
        super(position, dimension);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void update(long deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void onCollision(Entity other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }

    @Override
    public void jump() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'jump'");
    }

    @Override
    public void loseLife() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loseLife'");
    }

    @Override
    public boolean isOnPlatform() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOnPlatform'");
    }

    @Override
    public void changeState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeState'");
    }

    @Override
    public Vector2D getVelocity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVelocity'");
    }

    @Override
    public void setVelocity(Vector2D velocity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVelocity'");
    }

    @Override
    public boolean isFacingRight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFacingRight'");
    }

    @Override
    public void setFacingRight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFacingRight'");
    }

}
