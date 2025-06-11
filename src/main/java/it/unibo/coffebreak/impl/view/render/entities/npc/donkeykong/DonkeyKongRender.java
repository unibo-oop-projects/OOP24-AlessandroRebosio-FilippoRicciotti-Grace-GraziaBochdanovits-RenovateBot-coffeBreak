package it.unibo.coffebreak.impl.view.render.entities.npc.donkeykong;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the Antagonist that draws him as a yellow rectangle on the
 * screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class DonkeyKongRender extends AbstractEntityRender {

    /**
     * Constructs a new DonkeyKong with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public DonkeyKongRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof DonkeyKong) {
            g.setColor(Color.YELLOW);
            super.draw(g, entity, deltaTime, width, height);
        }
    }
}
