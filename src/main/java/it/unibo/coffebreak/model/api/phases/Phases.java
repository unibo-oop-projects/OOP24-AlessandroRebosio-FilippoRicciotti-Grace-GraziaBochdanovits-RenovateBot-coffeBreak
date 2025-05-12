package it.unibo.coffebreak.model.api.phases;

import it.unibo.coffebreak.controller.handle.Action;

public interface Phases {

    enum PhaseType {

        MENU,
        IN_GAME,
        PAUSE,
        GAME_OVER
    }

    void enterState();

    void exitState();

    void handleInput(Action action); 

    void update(long deltaTime);
}
