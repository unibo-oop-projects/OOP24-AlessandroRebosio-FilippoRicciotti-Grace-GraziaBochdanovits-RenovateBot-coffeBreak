package it.unibo.coffebreak.view;

import javax.swing.JPanel;

import it.unibo.coffebreak.controller.api.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameView extends JPanel implements KeyListener {

    private final Controller controller;

    public GameView(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.controller.notifyInput(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
