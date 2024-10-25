package io.github.kopake.spitball.game;

import android.os.Handler;
import android.os.Looper;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import io.github.kopake.spitball.game.event.EventHandler;
import io.github.kopake.spitball.game.event.EventManager;
import io.github.kopake.spitball.game.event.GameStartEvent;
import io.github.kopake.spitball.game.event.NextButtonPressEvent;
import io.github.kopake.spitball.game.event.NextWordEvent;
import io.github.kopake.spitball.game.event.RoundStartEvent;
import io.github.kopake.spitball.game.event.listeners.Listener;
import io.github.kopake.spitball.game.model.WordList;

public class NextWordChooser implements Listener {

    private static final NextWordChooser instance = new NextWordChooser();

    private String mostRecentWord = "";

    /**
     * The number of recent words that are stored to prevent repeating a word shortly after it was
     * recently used.
     */
    private static final int RECENCY_WORD_PREVENTION_DISTANCE = 50;

    private LinkedList<String> currentSetOfWords;

    private Set<String> wordsUsedThisSession = new HashSet<>();

    private Queue<String> recentlyUsedWords = new LinkedList<>();

    private NextWordChooser() {
    }

    public static NextWordChooser getInstance() {
        return instance;
    }

    public String getMostRecentWord() {
        return mostRecentWord;
    }

    /**
     * Calculates the next word and returns it.
     * <p>
     * The next word is chosen in the following priority order:
     * - A new word which has not been used yet this session
     * - If no such word exists, a new word which hasn't been used recently
     * - If no such word exists, the state is reset and starts from the beginning
     *
     * @return The next word
     * @throws RuntimeException If there are no words to choose from
     */
    private String getNextWord() {
        // Try to get a word that has not been used yet
        String nextWord = getNewWord(word -> !wordsUsedThisSession.contains(word));

        // If there are no words that have not been used, try to get a word that's not been used recently
        if (nextWord == null)
            nextWord = getNewWord(word -> !recentlyUsedWords.contains(word));

        // If there are no words available that have not been used recently, get any word we can (and shuffle the list so we don't loop through in the same order as before)
        if (nextWord == null) {
            wordsUsedThisSession.clear();
            recentlyUsedWords.clear();
            Collections.shuffle(currentSetOfWords);
            nextWord = getNewWord(word -> true);
        }

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
     * @return The next word from currentSetOfWords which satisfies the given predicate, or returns
     * null if there is no word in currentSetOfWords that satisfies the predicate
     */
    private String getNewWord(Predicate<String> validationPredicate) {
        for (int attempts = 0; attempts < currentSetOfWords.size(); attempts++) {
            cycleCurrentSetOfWords();
            String currentPotentialWord = currentSetOfWords.peek();
            if (validationPredicate.test(currentPotentialWord))
                return currentPotentialWord;
        }
        return null;
    }

    /**
     * Moves the front word in the current set to the end of the current set
     */
    private void cycleCurrentSetOfWords() {
        currentSetOfWords.offer(currentSetOfWords.remove());
    }

    /**
     * Updates local variables to treat the given word as used.
     * <p>
     * The word is added to the words used this session, and the recent words collection is updated
     * accordingly
     *
     * @param word The word to exhaust
     */
    private void exhaustWord(String word) {
        wordsUsedThisSession.add(word);

        recentlyUsedWords.add(word);
        if (recentlyUsedWords.size() > RECENCY_WORD_PREVENTION_DISTANCE)
            recentlyUsedWords.remove();
    }

    /**
     * Initializes the local variables so that they are ready for other method calls. Note that this
     * should be the first method called in the lifetime of this class
     *
     * @param gameStartEvent The game start event that triggered this method call
     */
    @EventHandler
    public void onGameStart(GameStartEvent gameStartEvent) {
        currentSetOfWords = new LinkedList<>();

        for (WordList wordList : gameStartEvent.getWordLists()) {
            currentSetOfWords.addAll(wordList.getWords());
        }
        Collections.shuffle(currentSetOfWords);
    }

    /**
     * Invokes {@link #calculateNextWordAndDispatchNextWordEvent(NextButtonPressEvent)}
     *
     * @param roundStartEvent The round start event that triggered this method call
     */
    @EventHandler
    public void onRoundStart(RoundStartEvent roundStartEvent) {
        calculateNextWordAndDispatchNextWordEvent(null);
    }

    /**
     * Calculates the next word that should be displayed and dispatches a
     * {@linkplain io.github.kopake.spitball.game.event.NextWordEvent}
     *
     * @param nextButtonPressEvent The event that (may have) triggered this method call (not used)
     */
    @EventHandler
    public void calculateNextWordAndDispatchNextWordEvent(NextButtonPressEvent nextButtonPressEvent) {
        String word = getNextWord();
        exhaustWord(word);
        mostRecentWord = word;
        // Run a little later so that the GameInProgressActivity can be created properly before the next word event is received
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            EventManager.getInstance().dispatchEvent(new NextWordEvent(word));
        }, 50);
    }
}
