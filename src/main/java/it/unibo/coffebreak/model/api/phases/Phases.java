package it.unibo.coffebreak.model.api.phases;

import it.unibo.coffebreak.controller.handle.Action;
import it.unibo.coffebreak.model.api.Model;

public interface Phases {

    enum PhaseType {

        MENU,
        IN_GAME,
        PAUSE,
        GAME_OVER
    }

    void enterState();

    void exitState();

    void handleInput(Action action,Model model); 

    void update(long deltaTime);
}
