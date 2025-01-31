package io.github.kopake.spitball.game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordList {

    private static final String WORD_LIST_COMMENT_START = "#";

    private String name;

    private List<String> words;

    public WordList(String name, InputStream inputStream) {
        this.name = name;
        this.words = getListOfWordsFromFile(inputStream);
    }

    private static List<String> getListOfWordsFromFile(InputStream inputStream) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
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

    public List<String> getWords() {
        return words;
    }

    public int getWordCount() {
        return words.size();
    }

    public String getName() {
        return name;
    }

    private static String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    @Override
    public String toString() {
        return getName();
    }
}
