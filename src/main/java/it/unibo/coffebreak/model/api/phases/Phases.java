package it.unibo.coffebreak.model.api.phases;

public interface Phases {

    void enterState();

    void exitState();

    void handleInput(); // TODO add Action

    void update(long deltaTime);
}
