
/**
* This class contains a program that takes in user input and converts it into a Māori sentence with the correct verb tense and pronoun. 
* It uses a pre-defined Map of verb tenses, and checks the user input to match one of the verb tenses in the Map. It then adds the correct pronoun
* based on the first word of the user input. The class has a main method that reads user input using a Scanner, and calls two methods to check 
* for the verb tense and pronoun, respectively. If either the verb or pronoun is invalid, it sets the output to "INVALID".
* The class also contains a static Map of String arrays called verbTenses, which maps a Māori verb to its three different English verb tenses. 
* It also has two static boolean variables, verb and pronoun, that determine whether a valid verb and pronoun have been found in the user input.
*/

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    /**
     * Map that stores verb tenses as keys and their corresponding Māori words as
     * values
     */
    public static Map<String, String[]> verbTenses = Map.of(
            "haere", new String[] { "went", "going", "go" },
            "hanga", new String[] { "made", "making", "make" },
            "kite", new String[] { "saw", "seeing", "see" },
            "hiahia", new String[] { "wanted", "wanting", "want" },
            "karanga", new String[] { "called", "calling", "call" },
            "pātai", new String[] { "asked", "asking", "ask" },
            "pānui", new String[] { "read", "reading", "read" },
            "ako", new String[] { "learned", "learning", "learn" });

    /**
     * Boolean variables that keep track if a valid pronoun and verb tense have been
     * found in the input sentence
     */
    public static boolean verb;
    public static boolean pronoun;

    /**
     * Main method that reads input sentences from the user, processes them and
     * prints the translated Māori sentences.
     * 
     * @param args command line arguments
     * @throws Exception if an error occurs while reading input from the user
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            StringBuilder output = new StringBuilder("");
            verb = false;
            pronoun = false;

            String userInput = sc.nextLine();
            String[] input = userInput.replaceAll("\\((.*?)\\)", " $1 ").split("\\s+");
            List<String> wordList = Arrays.asList(input);

            tenseAndVerbCheck(output, wordList);
            pronounCheck(output, wordList);
            outputPrint(output);
        }
    }

    /**
     * Checks if the input sentence contains a valid verb in the correct tense and
     * appends the corresponding Māori equivalents to the output StringBuilder.
     * 
     * @param output   StringBuilder object used to construct the output sentence
     * @param wordList List object that contains the input sentence
     */
    public static void tenseAndVerbCheck(StringBuilder output, List<String> wordList) {
        for (Map.Entry<String, String[]> entry : verbTenses.entrySet()) {
            if (wordList.get(wordList.size() - 1).equals(entry.getValue()[0])
                    && !wordList.get(wordList.size() - 2).equals("will")) {
                output.append("I ");
                verb = true;
            } else if (wordList.get(wordList.size() - 1).equals(entry.getValue()[1])
                    && (wordList.get(wordList.size() - 2).equals("am") || wordList.get(wordList.size() - 2).equals("is")
                            || wordList.get(wordList.size() - 2).equals("are"))) {
                output.append("Kei te ");
                verb = true;
            } else if (wordList.get(wordList.size() - 1).equals(entry.getValue()[2])
                    && wordList.get(wordList.size() - 2).equals("will")) {
                output.append("Ka ");
                verb = true;
            }
            for (int i = 0; i < entry.getValue().length; i++) {
                if (entry.getValue()[i].equalsIgnoreCase(wordList.get(wordList.size() - 1))) {
                    output.append(entry.getKey());
                    break;
                }
            }
        }
    }

    /**
     * This function checks the input word list for the pronoun and appends the
     * corresponding Māori language translation
     * to the output StringBuilder object. It also sets the value of the pronoun
     * boolean variable to true if a pronoun is
     * found in the input word list.
     * 
     * @param output   the StringBuilder object where the Māori language translation
     *                 of the pronoun will be appended
     * @param wordList the List of words in the input sentence
     */
    public static void pronounCheck(StringBuilder output, List<String> wordList) {
        // Check if the first word in the input word list is "I"
        if (wordList.get(0).equalsIgnoreCase("I")) {
            output.append(" au");
            pronoun = true;
            // Check if the first word in the input word list is "He" or "She"
        } else if (wordList.get(0).equalsIgnoreCase("He") || wordList.get(0).equalsIgnoreCase("She")) {
            output.append(" ia");
            pronoun = true;
            // Check if the first word in the input word list is "You"
        } else if (wordList.get(0).equalsIgnoreCase("You")) {
            // Check if the second word is "2" and the third word is "incl"
            if (wordList.get(1).equalsIgnoreCase("2") && wordList.get(2).equalsIgnoreCase("incl")) {
                output.append(" kōrua");
                pronoun = true;
                // Check if the word "incl" is present and the second word is greater than or
                // equal to 3
            } else if (wordList.contains("incl") && Integer.parseInt(wordList.get(1)) >= 3) {
                output.append(" koutou");
                pronoun = true;
                // Check if the word "excl" is present
            } else if (wordList.contains("excl")) {
                pronoun = false;
            } else {
                // If none of the above conditions are met, append "koe" to the output
                // StringBuilder object
                output.append(" koe");
                pronoun = true;
            }
            // Check if the first word in the input word list is "They"
        } else if (wordList.get(0).equalsIgnoreCase("They")) {
            // Check if the second word is "2" and the third word is "excl"
            if (wordList.get(1).equalsIgnoreCase("2") && wordList.get(2).equalsIgnoreCase("excl")) {
                output.append(" rāua");
                pronoun = true;
                // Check if the word "excl" is present and the second word is greater than or
                // equal to 3
            } else if (wordList.contains("excl") && Integer.parseInt(wordList.get(1)) >= 3) {
                output.append(" rātou");
                pronoun = true;
            }
            // Check if the first word in the input word list is "We"
        } else if (wordList.get(0).equalsIgnoreCase("We")) {
            // Check if the second word is "2" and the third word is "incl"
            if (wordList.get(1).equalsIgnoreCase("2") && (wordList.get(2).equalsIgnoreCase("incl"))) {
                output.append(" tāua");
                pronoun = true;
                // Check if the second word is "2" and the third word is "excl"
            } else if (wordList.get(1).equalsIgnoreCase("2") && (wordList.get(2).equalsIgnoreCase("excl"))) {
                output.append(" māua");
                pronoun = true;
                // Check if the word "excl" is present and the second word is greater than or
                // equal to 3
            } else if ((wordList.contains("excl")) && (Integer.parseInt(wordList.get(1)) >= 3)) {
                output.append(" mātou");
                pronoun = true;
                // Check if the word "incl" is present and the second word is greater than or
                // equal to 3
            } else if ((wordList.contains("incl")) && (Integer.parseInt(wordList.get(1)) >= 3)) {
                output.append(" tātou");
                pronoun = true;
            }
        }
    }

    /**
     * Outputs the final translation of the input sentence, based on the contents of
     * the StringBuilder object.
     * If the sentence contains an invalid pronoun or verb, the output will be
     * "INVALID".
     * 
     * @param output StringBuilder object containing the translated sentence
     */
    public static void outputPrint(StringBuilder output) {
        if (!pronoun || !verb) {
            output.replace(0, output.length(), "INVALID");
        }
        System.out.println(output);
    }
}