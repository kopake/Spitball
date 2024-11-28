package io.github.kopake.spitball.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

// Used to convert csvs (with a single header row) to word lists - made by chatgpt
// Might have to run with coverage in order to work inside of android studio
public class WordListGeneratorUtil {
    public static void convertCsvToText(String csvFilePath, String outputFilePath) {
        List<List<String>> columns = new ArrayList<>();
        Set<String> allEntriesSet = new HashSet<>();  // Set to track all unique entries across columns

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] headers = csvReader.readNext();
            if (headers != null) {
                // Initialize columns with headers
                for (String header : headers) {
                    List<String> column = new ArrayList<>();
                    column.add(header);
                    columns.add(column);
                }

                // Read each row, respecting quoted values
                String[] values;
                while ((values = csvReader.readNext()) != null) {
                    for (int i = 0; i < values.length; i++) {
                        if (i < columns.size()) {
                            columns.get(i).add(values[i]);
                        }
                    }
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return;
        }

        try (FileWriter writer = new FileWriter(outputFilePath)) {
            for (List<String> column : columns) {
                writer.write("#" + column.get(0) + "\n\n"); // Write header with surrounding blank lines

                // Create a TreeSet to sort and remove duplicates within the column, ignoring the header
                Set<String> sortedUniqueEntries = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                for (int i = 1; i < column.size(); i++) {
                    String word = column.get(i).toLowerCase().trim(); // Convert to lowercase and trim whitespace

                    // Ignore blank entries
                    if (word.isEmpty()) {
                        continue;
                    }

                    word = word.replace("’", "'");

                    // Check for special characters and print a message if found
                    if (!Pattern.matches("[a-zA-Z0-9 \\-\\'’.]+", word)) {
                        System.out.println("Encountered a non-letter character in word: " + word);
                    }

                    // Check for entries with more than 4 words
                    if (word.split("\\s+").length > 4) {
                        System.out.println("Warning: Entry with more than 4 words: \"" + word + "\"");
                    }

                    // Check for duplicates across columns
                    if (!allEntriesSet.add(word)) {  // If word is already in the set, it's a duplicate
                        System.out.println("Warning: Duplicate value detected across columns: \"" + word + "\"");
                    }

                    sortedUniqueEntries.add(word);
                }

                // Write sorted, unique entries to the output file
                for (String entry : sortedUniqueEntries) {
                    writer.write(entry + "\n");
                }
                writer.write("\n"); // Separate columns with a blank line
            }
            System.out.println("Conversion completed successfully. Output file created: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        String csvFilePath;
        String outputFilePath;

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Sci-Tech");
        System.out.println();
        System.out.println();
        System.out.println();
        csvFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\List - Sci-Tech.csv"; // Path to your CSV file
        outputFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\Sci-Tech.txt"; // Path for the output file
        convertCsvToText(csvFilePath, outputFilePath);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("The World");
        System.out.println();
        System.out.println();
        System.out.println();
        csvFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\List - The World.csv"; // Path to your CSV file
        outputFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\The_World.txt"; // Path for the output file
        convertCsvToText(csvFilePath, outputFilePath);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Entertainment");
        System.out.println();
        System.out.println();
        System.out.println();
        csvFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\List - Entertainment.csv"; // Path to your CSV file
        outputFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\Entertainment.txt"; // Path for the output file
        convertCsvToText(csvFilePath, outputFilePath);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Food");
        System.out.println();
        System.out.println();
        System.out.println();
        csvFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\List - Food.csv"; // Path to your CSV file
        outputFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\Food.txt"; // Path for the output file
        convertCsvToText(csvFilePath, outputFilePath);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Fun and Games");
        System.out.println();
        System.out.println();
        System.out.println();
        csvFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\List - Sports & Games.csv"; // Path to your CSV file
        outputFilePath = "C:\\Users\\Kole\\OneDrive\\Desktop\\Catchphrase_Word_Lists\\Fun_and_Games.txt"; // Path for the output file
        convertCsvToText(csvFilePath, outputFilePath);
    }
}
