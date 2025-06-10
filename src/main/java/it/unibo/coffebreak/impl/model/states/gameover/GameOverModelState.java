package it.unibo.coffebreak.impl.model.states.gameover;

import java.util.Collections;
import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

/**
 * Implementation of {@link ModelState} interface;
 * <p>
 * Represents the <b>Game Over</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class GameOverModelState extends AbstractModelState {

    private static final List<Option> OPTIONS = List.of(Option.CHAR0, Option.CHAR1, Option.CHAR2, Option.EXIT);
    private int selectedOption;
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private int index;
    // TODO: fix name pass from model
    private String name = "---";

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                if (Option.EXIT == OPTIONS.get(selectedOption) && !name.contains("-"))
                    model.setState(MenuModelState::new);
                break;
            case MOVE_UP:
                this.selectedOption = (this.selectedOption - 1 + OPTIONS.size()) % OPTIONS.size();
                if (this.selectedOption < 3) {
                    this.index = getIndex(name.charAt(selectedOption));
                }
                break;
            case MOVE_DOWN:
                this.selectedOption = (this.selectedOption + 1) % OPTIONS.size();
                if (this.selectedOption < 3) {
                    this.index = getIndex(name.charAt(selectedOption));
                }
                break;
            case MOVE_LEFT:
                if (this.selectedOption < 3) {
                    previousAlphabetChar(selectedOption);
                }

                break;
            case MOVE_RIGHT:
                if (this.selectedOption < 3) {
                    nextAlphabetChar(selectedOption);
                }
                break;
            default:
                break;
        }
    }

    private int getIndex(char c) {

        for (int i = 0; i < alphabet.length; i++) {
            if (c == alphabet[i]) {
                return i;
            }
        }
        return -1;
    }

    private void previousAlphabetChar(int nameIndex) {
        StringBuilder myName = new StringBuilder(this.name);
        this.index = (this.index - 1 + alphabet.length) % alphabet.length;
        myName.setCharAt(nameIndex, alphabet[this.index]);
        this.name = myName.toString();

    }

    private void nextAlphabetChar(int nameIndex) {
        StringBuilder myName = new StringBuilder(this.name);
        this.index = (this.index + 1) % alphabet.length;
        myName.setCharAt(nameIndex, alphabet[this.index]);
        this.name = myName.toString();

    }

    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
        model.addEntry(this.name);
    }

    @Override
    public Option getSelectedOption() {
        return OPTIONS.get(this.selectedOption);
    }

    @Override
    public List<Option> getOptions() {
        return Collections.unmodifiableList(OPTIONS);
    }
}
