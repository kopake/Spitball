package io.github.kopake.spitball.event;

import io.github.kopake.spitball.game.model.WordList;

public class WordListSelectEvent implements Event {

    private final WordList wordList;

    private final boolean selected;

    public WordListSelectEvent(WordList wordList, boolean selected) {
        this.wordList = wordList;
        this.selected = selected;
    }

    public WordList getWordList() {
        return wordList;
    }

    public boolean isSelected() {
        return selected;
    }
}
