package it.unibo.coffebreak.model.api.phases;

public interface Phases {

    enum PhaseType {

        MENU,
        IN_GAME,
        PAUSE,
        GAME_OVER
    }

    void enterState();

    void exitState();

    void handleInput(); // TODO add Action

    void update(long deltaTime);
}
