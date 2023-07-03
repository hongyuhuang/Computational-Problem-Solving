import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * This class is for if you want to test our backup logic for when a word is not found in the dictionary.
 * There are some testing functions that print off the % accuracy and list of incorrect words.
 */
public class SyllableSlamTest {
    static int colonCount;
    static double wordCount = 0;
    static double correctCount = 0;
    static ArrayList<String> incorrectSyllables = new ArrayList<String>();
    static HashMap<String, Integer> dictOfWords = new HashMap<String, Integer>();

    public static void main(String[] args) throws FileNotFoundException {
        // Open file which will be our dictionary
        File file = new File("25K-syllabified-sorted-by-prevalence.txt");

        // Create Scanner to read from the syllable file
        Scanner s = new Scanner(file);
        // For each line
        while (s.hasNextLine()) {
            colonCount = 0;
            // Store word in line
            String inputWithColons = s.nextLine();
            // Store word without syllables
            String input = inputWithColons.replaceAll(";", "");
            // Count syllables in word to get syllables in word
            for (int i = 0; i < inputWithColons.length(); i++) {
                if (inputWithColons.charAt(i) == ';') {
                    colonCount++;
                }
            }
            // Added word and its syllable count to dictionary hashmap
            dictOfWords.put(input, colonCount + 1);
        }
        s.close();

        // Scanner for user inputted
        Scanner scan = new Scanner(System.in);
        // While input has a next line
        while (scan.hasNextLine()) {
            // Store input of string
            colonCount = 0;
            // String input = scan.nextLine();
            String input = scan.nextLine();
            // For testing purposes
            String inputS = input.replaceAll(";", "");
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == ';') {
                    colonCount++;
                }
            }
            // Check if word inputted is valid
            if (inputS.matches("[a-zA-Z]+")) {
                // Count syllables in word
                countSyllables(inputS);
            } else {
                // Print error if not
                System.out.println(inputS + " - INVALID");
                System.err.println("Please enter a valid word");
            }
        }
        // Close scanner
        scan.close();
        showPercentage();
    }

    /**
     * This method takes a String input and counts the number of syllables in it.
     * It uses a HashSet of vowels to determine if a character is a vowel or not.
     * If the input is present in the dictionary of words, it returns the value of
     * the key from the hashmap.
     * Otherwise, it loops through the input character array and counts the number
     * of syllables based on certain rules.
     * The final count of syllables is stored in the variable "syllables".
     * 
     * @param input the string for which we want to count the number of syllables
     */
    public static void countSyllables(String input) {
        // Keep count of syllables
        int syllables = 0;
        // Store vowels in HashSet
        HashSet<Character> vowels = new HashSet<>(List.of('a', 'e', 'i', 'o', 'u', 'y'));
        // Change and split word to lowercase
        char[] inputArray = input.toLowerCase().toCharArray();
        // Loop through input array
        for (int i = 0; i < inputArray.length; i++) {
            char c = inputArray[i];
            // Vowel found
            if (vowels.contains(c)) {
                // Vowel next to current vowel found
                if (i > 0 && vowels.contains(inputArray[i - 1])) {
                    syllables--;
                }
                syllables++;
            }
            if (c == 'o' && i > 0 && inputArray[i - 1] == 'e') {
                syllables++;
            }
        }
        if (input.endsWith("e") && !input.endsWith("le")) {
            syllables--;
        } else if (input.endsWith("le") && vowels.contains(inputArray[input.lastIndexOf("le") - 1])) {
            syllables--;
        }
        if ((input.endsWith("ing") && vowels.contains(inputArray[input.indexOf("ing") - 1])) ||
                input.endsWith("ated") && vowels.contains(inputArray[input.indexOf("ated") - 1])) {
            syllables++;
        } else if (input.endsWith("lised") || input.endsWith("eful") || input.endsWith("ely") ||
                input.endsWith("pped")) {
            syllables--;
        } else if (input.endsWith("pped")) {
            syllables = 1;
        } else if (syllables == 0) {
            syllables = 1;
        }
        System.out.println(syllables);
        wordCount++;
        if (syllables == colonCount + 1) {
            correctCount++;
        } else {
            incorrectSyllables.add(input);
        }
    }

    public static void showPercentage() {
        double percentage = (correctCount / wordCount) * 100;
        String roundedNumber = String.format("%.2f", percentage);

        System.out.println(incorrectSyllables);
        System.out.println("Percentage of correct words is " + roundedNumber + "%");
    }
}