import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;  
import java.util.Comparator; 
import java.util.List;  
import java.util.Map.Entry; 
import java.util.LinkedList;

public class Main {

    /**
     * Converts to words map to a linkedList and sorts it in descending order.
     * @param words the map of words and their occurrences.
     * @return the sorted list of words and their occurrences.
     */
    private static List<Entry<String, Integer>> sort(Map< String, Integer> words) {
        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(words.entrySet());  
        
        //sorting the list elements  
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {  
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) { 
                return o2.getValue().compareTo(o1.getValue());   
            }  
        }); 
        return list; 
    }

    /**
     * Counts the number of occurrences for each word in 'file2Search' while
     * ignoring any words from 'commonWordsFile'.
     * @param file2Search the file in which we will count word occurrences.
     * @param commonWordsFile file containing list of common words to omit.
     * @param words map that will store all words and their occurrences.
     */
    private static void countWords(String file2Search, String commonWordsFile, Map< String, Integer> words) {

        HashSet<String> commonWords = new HashSet<String>();  

        Scanner scanner1 = null;
        Scanner scanner2 = null;

        try {
            
            scanner1 = new Scanner (new File(file2Search));
            scanner2 = new Scanner (new File(commonWordsFile));

            //Load in the common words file
            while(scanner2.hasNext()){
                String word = scanner2.next();
                //toLowerCase is called because 'I' is capitalized.
                commonWords.add(word.toLowerCase());
            }

            //Load in the english text file and count the occurrences of each word.
            while(scanner1.hasNext()){

                String word = cleanWord(scanner1.next());
                if(word.isEmpty()){
                    continue;
                }

                Integer count = words.get(word);
                if(count != null) {
                    count++;
                } else {
                    count=1;
                }

                //Exclude words that are common words
                if(!commonWords.contains(word)){
                    words.put(word,count);
                }
            }

        } catch (FileNotFoundException e) {

            System.out.println("Input file(s) was not found: " + e );
            System.exit(1);

        } finally {
            if(scanner1 != null ) {
                scanner1.close();
            }
            if(scanner2 != null){
                scanner2.close();
            }
        }
        
    }

    /**
     * Makes words lowercase and removes unwanted characters.
     * @param word the word to clean up.
     * @return a clean word.
     */
    private static String cleanWord(String word){
        String cleanWord = word.toLowerCase();
        //TODO: Questions on how to handle words divided by apostrophes.
        return cleanWord.replaceAll("\\W","");
    }

    /**
     * Prints the 'n' number of words from the passed in list.
     * @param list the list of words to print out.
     */
    private static void printWords(List<Entry<String, Integer>> list, int n) {

        System.out.println("\nCount Word\n");
        System.out.format("===%3s====%n", "");

        //Print the first n elements as long as n is not greater than the size of the list.
        for(int i = 0; i < n && i < list.size(); i++) {
            System.out.println("\n" + list.get(i).getValue() + "\t" + list.get(i).getKey());
        }
    }

    /**
     * Main method. Start of program.
     */
    public static void main(String[] args) {

        String file2Search = args[0];
        String commonWordsFile = args[1];
        int n = Integer.parseInt(args[2]);

        Map<String,Integer> words = new HashMap<String, Integer>();

        countWords(file2Search, commonWordsFile, words);
        printWords(sort(words),n);
       
    }
}