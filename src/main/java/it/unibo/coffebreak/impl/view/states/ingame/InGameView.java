package it.unibo.coffebreak.impl.view.states.ingame;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;
import it.unibo.coffebreak.impl.view.render.entities.mario.PlayerRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.ladder.LadderRender;
import it.unibo.coffebreak.impl.view.render.entities.structure.platform.PlatformRender;
import it.unibo.coffebreak.impl.view.states.AbstractViewState;

/**
 * Represents the in-game view state of the game.
 * This class handles the rendering of game entities during gameplay
 * using a dedicated RenderManager.
 * 
 * <p>
 * It extends {@link AbstractViewState} to inherit common view state
 * functionality
 * and implements the game-specific rendering logic.
 * </p>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class InGameView extends AbstractViewState {

    private final EntityRender plafformRender = new PlatformRender(super.getResource());
    private final EntityRender playerRender = new PlayerRender(super.getResource());
    private final EntityRender laddRender = new LadderRender(super.getResource());

    /**
     * Constructs an InGameView with the specified controller.
     * Initializes the render manager with a default resolution of 800x600 pixels.
     * 
     * @param controller the game controller that manages the game logic and
     *                   entities
     * @throws IllegalArgumentException if the controller is null
     */
    public InGameView(final Controller controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final int width, final int height, final float deltaTime) {
        super.getController().getEntities().forEach(t -> {
            if (t instanceof Platform) {
                this.plafformRender.draw(g, t, deltaTime);
            }
            if (t instanceof MainCharacter) {
                this.playerRender.draw(g, t, deltaTime);
            }
            if (t instanceof Ladder) {
                this.laddRender.draw(g, t, deltaTime);
            }
        });
    }
}
