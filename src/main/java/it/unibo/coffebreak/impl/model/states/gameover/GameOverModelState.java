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
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private int selectedOption;
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
                if (Option.EXIT == OPTIONS.get(selectedOption) && !name.contains("-")) {
                    model.setState(MenuModelState::new);
                }
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

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Option getSelectedOption() {
        return OPTIONS.get(this.selectedOption);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Option> getOptions() {
        return Collections.unmodifiableList(OPTIONS);
    }

    /**
     * Getter of the index in the alphabet of the current char.
     * 
     * @param c whose index we need to find
     * 
     * @return position in the alphabet of the current char
     */
    private int getIndex(final char c) {

        for (int i = 0; i < ALPHABET.length; i++) {
            if (c == ALPHABET[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Sets the current char of the name String to the previous corresponding char
     * of the alphabet.
     * 
     * @param nameIndex index of the current char of the nameString we have to set.
     */
    private void previousAlphabetChar(final int nameIndex) {
        final StringBuilder myName = new StringBuilder(this.name);
        this.index = (this.index - 1 + ALPHABET.length) % ALPHABET.length;
        myName.setCharAt(nameIndex, ALPHABET[this.index]);
        this.name = myName.toString();

    }

    /**
     * Sets the current char of the name String to the next corresponding char
     * of the alphabet.
     * 
     * @param nameIndex index of the current char of the nameString we have to set.
     */
    private void nextAlphabetChar(final int nameIndex) {
        final StringBuilder myName = new StringBuilder(this.name);
        this.index = (this.index + 1) % ALPHABET.length;
        myName.setCharAt(nameIndex, ALPHABET[this.index]);
        this.name = myName.toString();

    }
}
