package io.github.kopake.catchphrase.game;

import android.widget.Toast;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import io.github.kopake.catchphrase.Catchphrase;
import io.github.kopake.catchphrase.file.WordListParser;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.NextWordEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;
import io.github.kopake.catchphrase.game.model.WordList;

public class CurrentWord implements Listener {

    private static final int RECENCY_WORD_PREVENTION_DISTANCE = 2;

    private LinkedList<String> currentSetOfWords;

    private Set<String> wordsUsedThisSession = new HashSet<>();

    private Queue<String> recentlyUsedWords = new LinkedList<>();

    public void showNextWord() {
        String word = getNextWord();
        Toast.makeText(Catchphrase.getContext(), word, Toast.LENGTH_SHORT).show();
        exhaustWord(word);
    }


    private String getNextWord() {
        // Try to get a word that has not been used yet
        String nextWord = getNewWord(word -> !wordsUsedThisSession.contains(word));

        // If there are no words that have not been used, try to get a word that's not been used recently
        if (nextWord == null)
            nextWord = getNewWord(word -> !recentlyUsedWords.contains(word));

        // If there are no words available that have not been use recently, get any word we can
        if (nextWord == null)
            nextWord = getNewWord(word -> true);

        // If there are still no words available, then there are no words to select from
        if (nextWord == null)
            throw new RuntimeException("There are no words to select from");

        return nextWord;
    }

    /**
     * Returns the next word from currentSetOfWords which satisfies the given predicate.
     * <p>
     * Returns null if there are no words in currentSetOfWords which satisfy the predicate.
     *
     * @param validationPredicate The satisfaction criteria for the word returned
     * @return The next word from currentSetOfWords which satisfies the given predicate
     */
    private String getNewWord(Predicate<String> validationPredicate) {
        for (int attempts = 0; attempts < currentSetOfWords.size(); attempts++) {
            String currentPotentialWord = currentSetOfWords.peek();
            if (validationPredicate.test(currentPotentialWord))
                return currentPotentialWord;
            cycleCurrentSetOfWords();
        }
        return null;
    }

    private void cycleCurrentSetOfWords() {
        currentSetOfWords.offer(currentSetOfWords.remove());
    }

    private void exhaustWord(String word) {
        wordsUsedThisSession.add(word);

        recentlyUsedWords.add(word);
        if (recentlyUsedWords.size() > RECENCY_WORD_PREVENTION_DISTANCE)
            recentlyUsedWords.remove();
    }

    @EventHandler
    public void onGameStart(NextWordEvent gameStartEvent) {
        currentSetOfWords = new LinkedList<>();

        //TODO make this only the selected lists
        for (WordList wordList : WordListParser.getAllWordLists()) {
            currentSetOfWords.addAll(wordList.getWords());
        }
        Collections.shuffle(currentSetOfWords);

        showNextWord();
    }

}
