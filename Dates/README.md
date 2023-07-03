# COSC326 Etude 1

**Date.java** is the support class that does most of the date parsing, while **DateApp.java** is the application class where each Date is created. This can be run in either of two ways (in VSCode):
1. By hitting "Run" and then manually inputting dates in.
2. Through the terminal and giving it a test file to run, e.g. `java DateApp.java < valid_dates.txt` This can be outputted through the terminal or into a text file by adding ">" to some text file to the previous command e.g. `java DateApp.java < valid_dates.txt. > output.txt`

---

**ValidDates.java** is the support program that generated all the valid dates in **valid_dates.txt**. This was done by running `java ValidDates.java > valid_dates.txt` in the terminal. 

**invalid_dates.txt** was written by hand.

Both of these text files were used to thorughly test **DateApp.java**. The program was first tested against all the valid dates to make sure it could process all the possibliltes. These tests went pretty well and there were only a few edge cases that weren't being considered. AutoJudge was then used which showed there was probably invalid cases that were being considered valid. This highlighted the importance that any date that wasn't in the following format should be considered invalid, leading to a few more cases being written.

These two text files also formed the basis of the JUnit tests that do some automated testing around **Date.java**.