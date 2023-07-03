# COSC326 Etude 2 - Syllable Slam
# Hongyu's Homies

Can you teach a computer to count syllable in a word? Given a list of words (one per line) inputted, our program Syllable Slam will print out the amount of syllables in that word.

In our submission you will find two Java files:
`SyllableSlam.java` is our final application that can count syllables in a word.
`SyllableSlamTest.java` is our testing file for checking the accuracy of our fallback rules (that are run when the word is not found in the dictionary) - this file includes methods that will print out the % accuracy of the fallback rules and will print out the incorrect words. This program was also used to test against multiple .txt files that contained a variety of words ranging in syllable counts.

---
## **How to run:**
`SyllableSlamTest.java`
To run this program, you type the following into the terminal:
```
java SyllableSlamTest.java < "name of one of the included .txt files"
```

`SyllableSlam.java` 
To run this program, use the run function on your IDE and proceed to input words to have their syllables counted. Get ready to have some fun!

---

## Our program

The method begins by using a scanner to iterate through each of the 25,000 words in "25K-syllabified-sorted-by-prevalence.txt" in which the syllables of each word are separated by a colon. For each word read the program counts the number of colons and removes them, adding the newly formatted word to a hashmap as the key, and the number of colons in that word (plus 1) as the key (which represents the number of syllables in the given word).

The program is now ready to read user input. The program checks if the input is correctly formatted by making sure it is composed of only alphabet characters. The hashmap is search for the input, and if present, the value of the input in the hashmap is returned. If the input is not present in the hash map, it is checked against the following rules in order to estimate the number of syllables present.

The estimated number of syllables ("count") begins at zero and is incremented or decremented based on patterns which inform common (and uncommon) syllable rules and exceptions.

The first section formats the user input into lowercase and a char array. It then proceeds to loop through each letter and check for vowels. If a vowel is present, the syllable count will increment EXCEPT if there is are consecutive vowels in which it will decrement the count.

The second section adjusts the syllable count based on more general rules and exceptions:
    - If the word contains "eo"
    - Words ending in "ing", "ated", "lised", "eful", "ely", and "pped".
    - Words must have at least 1 syllable.

---

## Testing
For testing purposes we created the SyllableSlamTest.java file to test and check the accuracy our set of back up rules when compared against our library .txt files. These .txt files were found on GitHub under permissions of private and commercial use, modification and distribution (https://github.com/gautesolheim/25000-syllabified-words-list). Beyond that, this test java file was also used to test the accuracy of our main method of counting syllables (finding the word in the dictionary).

Auto judge was also used to confirm our accuracy with further feedback provided from teaching staff to aid us in our development.

## Authors

**Hongyu's Homies:** 
- *Hongyu Huang*
- *Jay Lee*
- *Michael Young*
- *Luke Piper*
