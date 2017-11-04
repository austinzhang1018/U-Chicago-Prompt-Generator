import java.io.*;
import java.util.*;

/**
 * Created by Austin Zhang on 8/10/2017.
 * Not necessarily the most efficient solution (I'm sure I can improve the runtime)
 * but it doesn't really matter for the set that I'm using.
 * The algorithm converts a text document into a set of words and permutes those words according to the
 * U Chicago 2017 essay prompt #2 guidelines. Adds the words to a map of before and after words and outputs
 * them to a text document.
 *
 * This program is designed to help with possible ideas for essay topics.
 */
public class WordGenerator {
    private static final String FILENAME = "uchicagoMajors.txt";

    private static HashSet<String> allEnglishWords;
    private static BufferedReader wordListReader;
    private static BufferedReader uChicagoMajorsReader;
    private static HashSet<String> uChicagoMajorWords;
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private static HashMap<String, String> wordsCreated;


    public static void main(String[] args) throws IOException {
        wordsCreated = new HashMap<String, String>();

        //Adding the english word dictionary to a set (the word list we're using has 172820 words)
        allEnglishWords = new HashSet<String>();
        wordListReader = new BufferedReader(new FileReader("enable1.txt"));
        String word = wordListReader.readLine();
        while (word != null) {
            allEnglishWords.add(word.toLowerCase());
            word = wordListReader.readLine();
        }

        //Now do the same except for the words in the list of majors.
        //This code also gets each individual word in multi-word majors
        uChicagoMajorWords = new HashSet<String>();
        uChicagoMajorsReader = new BufferedReader(new FileReader(FILENAME));
        String major = uChicagoMajorsReader.readLine();
        while (major != null) {
            Scanner scanner = new Scanner(major);
            while (scanner.hasNext()) {
                uChicagoMajorWords.add(scanner.next().toLowerCase());
            }
            major = uChicagoMajorsReader.readLine();
        }

        //System.out.println(uChicagoMajorWords.toString());

        for (String wordInMajor : uChicagoMajorWords) {
            //first find all the words that can be created by inserting a letter
            for (int i = 0; i <= wordInMajor.length(); i++) {
                for (int s = 0; s < alphabet.length(); s++) {
                    String possibleWord = wordInMajor.substring(0, i) + alphabet.substring(s, s + 1) + wordInMajor.substring(i, wordInMajor.length());
                    if (allEnglishWords.contains(possibleWord) && !possibleWord.equals(wordInMajor)) {
                        wordsCreated.put(possibleWord, wordInMajor);
                    }
                }
            }

            //next find all the words that can be created by deleting or modifying a letter
            for (int i = 0; i < wordInMajor.length(); i++) {
                String possibleWordFromDeletion = wordInMajor.substring(0, i) + wordInMajor.substring(i + 1, wordInMajor.length());
                if (allEnglishWords.contains(possibleWordFromDeletion) && !possibleWordFromDeletion.equals(wordInMajor)) {
                    wordsCreated.put(possibleWordFromDeletion, wordInMajor);
                }

                for (int s = 0; s < alphabet.length();s++) {
                    String possibleWordFromModification = wordInMajor.substring(0,i) + alphabet.substring(s, s+1) + wordInMajor.substring(i+1, wordInMajor.length());
                    if (allEnglishWords.contains(possibleWordFromModification) && !possibleWordFromModification.equals(wordInMajor)) {
                        wordsCreated.put(possibleWordFromModification, wordInMajor);
                    }
                }

            }
        }

        System.out.println(wordsCreated.toString());

        //write out to a file
        PrintWriter writer = new PrintWriter(new FileWriter("results.txt"));
        writer.println("possible new words created from list of U Chicago Majors");
        writer.println();

        for (String newWord : wordsCreated.keySet()) {
            writer.println(wordsCreated.get(newWord) + " --> " + newWord);
        }

        writer.close();
    }

}

