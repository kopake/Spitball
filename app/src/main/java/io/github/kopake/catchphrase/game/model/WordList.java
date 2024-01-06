package io.github.kopake.catchphrase.game.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordList {

    private static final String WORD_LIST_COMMENT_START = "#";
    private File wordListFile;

    private List<String> words;

    public WordList(File wordListFile) {
        if (wordListFile == null)
            throw new IllegalArgumentException("WordList could not be created for null file");
        if (!wordListFile.exists())
            throw new IllegalArgumentException("WordList could not be created for non-existent file: " + wordListFile.getAbsolutePath());

        this.wordListFile = wordListFile;
        this.words = getListOfWordsFromFile(wordListFile);
    }

    private static List<String> getListOfWordsFromFile(File wordListFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(wordListFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith(WORD_LIST_COMMENT_START)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }


    public File getWordListFile() {
        return wordListFile;
    }

    public List<String> getWords() {
        return words;
    }

    public int getWordCount() {
        return words.size();
    }

    public String getName() {
        return getFileNameWithoutExtension(wordListFile.getName());
    }

    private static String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
}
