package it.unibo.coffebreak.model.impl.phases;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;

public abstract class AbstractPhases implements Phases {

    @Override
    public void enterPhase() {
    }

    @Override
    public void exitPhase() {
    }

    @Override
    public abstract void handleAction(Command action, Model model);

    @Override
    public void update(long deltaTime) {
    }

}
