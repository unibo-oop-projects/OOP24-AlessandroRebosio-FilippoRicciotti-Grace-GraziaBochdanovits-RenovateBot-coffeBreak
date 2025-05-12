package it.unibo.coffebreak.model.impl.entities.collectible;

import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;

public class Coin extends AbstractCollectible {

    public Coin(final Position2D position, final Dimension2D dimension, final int value) {
        super(position, dimension, value);
    }

    @Override
    protected void applyEffect(final Character player) {
        // TODO: player.getScoreManager().earnPoints(super.value);
    }

}
