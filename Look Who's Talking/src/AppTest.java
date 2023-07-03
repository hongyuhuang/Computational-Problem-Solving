import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AppTest {

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "go" verb.
     * 
     * @throws Exception
     */
    @Test
    public void goVerbTest() throws Exception {
        tenseAndVerb("I haere", Arrays.asList("you", "went"));
        tenseAndVerb("Kei te haere", Arrays.asList("you", "are", "going"));
        tenseAndVerb("Ka haere", Arrays.asList("you", "will", "go"));
    }

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "make" verb.
     * 
     * @throws Exception
     */
    @Test
    public void makeVerbTest() throws Exception {
        tenseAndVerb("I hanga", Arrays.asList("you", "made"));
        tenseAndVerb("Kei te hanga", Arrays.asList("you", "are", "making"));
        tenseAndVerb("Ka hanga", Arrays.asList("you", "will", "make"));
    }

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "see" verb.
     * 
     * @throws Exception
     */
    @Test
    public void seeVerbTest() throws Exception {
        tenseAndVerb("I kite", Arrays.asList("you", "saw"));
        tenseAndVerb("Kei te kite", Arrays.asList("you", "are", "seeing"));
        tenseAndVerb("Ka kite", Arrays.asList("you", "will", "see"));
    }

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "want" verb.
     * 
     * @throws Exception
     */
    @Test
    public void wantVerbTest() throws Exception {
        tenseAndVerb("I hiahia", Arrays.asList("you", "wanted"));
        tenseAndVerb("Kei te hiahia", Arrays.asList("you", "are", "wanting"));
        tenseAndVerb("Ka hiahia", Arrays.asList("you", "will", "want"));
    }

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "call" verb.
     * 
     * @throws Exception
     */
    @Test
    public void callVerbTest() throws Exception {
        tenseAndVerb("I karanga", Arrays.asList("you", "called"));
        tenseAndVerb("Kei te karanga", Arrays.asList("you", "are", "calling"));
        tenseAndVerb("Ka karanga", Arrays.asList("you", "will", "call"));
    }

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "ask" verb.
     * 
     * @throws Exception
     */
    @Test
    public void askVerbTest() throws Exception {
        tenseAndVerb("I pātai", Arrays.asList("you", "asked"));
        tenseAndVerb("Kei te pātai", Arrays.asList("you", "are", "asking"));
        tenseAndVerb("Ka pātai", Arrays.asList("you", "will", "ask"));
    }

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "read" verb.
     * 
     * @throws Exception
     */
    @Test
    public void readVerbTest() throws Exception {
        tenseAndVerb("I pānui", Arrays.asList("you", "read"));
        tenseAndVerb("Kei te pānui", Arrays.asList("you", "are", "reading"));
        tenseAndVerb("Ka pānui", Arrays.asList("you", "will", "read"));
    }

    /**
     * Uses the tenseAndVerb function to test for the correct translations of the
     * different tenses of the "learn" verb.
     * 
     * @throws Exception
     */
    @Test
    public void learnVerbTest() throws Exception {
        tenseAndVerb("I ako", Arrays.asList("you", "learned"));
        tenseAndVerb("Kei te ako", Arrays.asList("you", "are", "learning"));
        tenseAndVerb("Ka ako", Arrays.asList("you", "will", "learn"));
    }

    /**
     * Uses the pronoun Function to test for the correct translation of the "I"
     * pronoun
     * 
     * @throws Exception
     */
    @Test
    public void iPronounTest() throws Exception {
        pronoun(" au", Arrays.asList("I", "will", "learn"));
    }

    /**
     * Uses the pronoun Function to test for the correct translation of the "he" and
     * "she" pronoun
     * 
     * @throws Exception
     */
    @Test
    public void heShePronounTest() throws Exception {
        pronoun(" ia", Arrays.asList("he", "will", "learn"));
        pronoun(" ia", Arrays.asList("she", "will", "learn"));
    }

    /**
     * Uses the pronoun Function to test for the correct translation of the "you"
     * pronoun and the different variations
     * depending on the number of participants
     * 
     * @throws Exception
     */
    @Test
    public void youPronounTest() throws Exception {
        pronoun(" koe", Arrays.asList("you", "will", "learn"));
        pronoun(" kōrua", Arrays.asList("you", "2", "incl", "will", "learn"));
        pronoun(" koutou", Arrays.asList("you", "3", "incl", "will", "learn"));
        pronoun(" koutou", Arrays.asList("you", "6", "incl", "will", "learn"));
        pronoun(" koutou", Arrays.asList("you", "15", "incl", "will", "learn"));
    }

    /**
     * Uses the pronoun Function to test for the correct translation of the "they"
     * pronoun and the different variations
     * depending on the number of participants
     * 
     * @throws Exception
     */
    @Test
    public void theyPronounTest() throws Exception {
        pronoun(" rāua", Arrays.asList("they", "2", "excl", "will", "learn"));
        pronoun(" rātou", Arrays.asList("they", "3", "excl", "will", "learn"));
        pronoun(" rātou", Arrays.asList("they", "6", "excl", "will", "learn"));
        pronoun(" rātou", Arrays.asList("they", "15", "excl", "will", "learn"));
    }

    /**
     * Uses the pronoun Function to test for the correct translation of the "we"
     * pronoun and the different variations
     * depending on the number of participants
     * 
     * @throws Exception
     */
    @Test
    public void wePronounTest() throws Exception {
        pronoun(" tāua", Arrays.asList("we", "2", "incl", "will", "learn"));
        pronoun(" tātou", Arrays.asList("we", "3", "incl", "will", "learn"));
        pronoun(" tātou", Arrays.asList("we", "10", "incl", "will", "learn"));
        pronoun(" māua", Arrays.asList("we", "2", "excl", "will", "learn"));
        pronoun(" mātou", Arrays.asList("we", "3", "excl", "will", "learn"));
        pronoun(" mātou", Arrays.asList("we", "15", "excl", "will", "learn"));
    }

    /**
     * This function checks the emulated input and passes it into the App.java
     * tenseAndVerbCheck function where it will
     * then write to the StringBuilder testOutput variable the correct Māori
     * translation.
     * 
     * @param expected String that is used as a comparator for the expected output
     *                 of the function we are testing
     * @param input    List<String> that is emulating the user input we are going to
     *                 be translating
     */
    public void tenseAndVerb(String expected, List<String> input) {
        StringBuilder expectedOutput = new StringBuilder(expected);
        StringBuilder testOutput = new StringBuilder("");
        App.tenseAndVerbCheck(testOutput, input);
        assertEquals(expectedOutput.toString(), testOutput.toString());
    }

    /**
     * This function checked the emulated input and passes it into the App.java
     * pronounCheck function where it will
     * then write to the StringBuilder testOutput variable the correct Māori
     * translation.
     * 
     * @param expected String that is used as a comparator for the expected output
     *                 of the function we are testing
     * @param input    List<String> that is emulating the user input we are going to
     *                 be translating
     */
    public void pronoun(String expected, List<String> input) {
        StringBuilder expectedOutput = new StringBuilder(expected);
        StringBuilder testOutput = new StringBuilder("");
        App.pronounCheck(testOutput, input);
        assertEquals(expectedOutput.toString(), testOutput.toString());
    }
}
