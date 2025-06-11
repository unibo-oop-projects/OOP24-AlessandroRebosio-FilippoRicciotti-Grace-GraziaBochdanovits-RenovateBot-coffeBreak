package it.unibo.coffebreak.impl.view.render.entities.enemy.fire;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.enemy.AbstractEnemyRender;

/**
 * A renderer for Fire entities that draws them as red circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class FireRender extends AbstractEnemyRender {

    /**
     * Constructs a new FireRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     */
    public FireRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderEnemy(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        g.setColor(Color.RED);
    }
}
