package me.dfastje.ScrabbleProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  Random things I've looked up:
 *      Words taken from https://www.ef.com/wwen/english-resources/english-vocabulary/top-3000-words/
 *      File reading adapted from: https://mkyong.com/java/java-how-to-read-a-file-into-a-list/
 *      Getting file from resources directory: https://mkyong.com/java/java-read-a-file-from-resources-folder/
 *      Default springboot logging: https://www.baeldung.com/spring-boot-logging
 *      Javadoc variable reference: https://stackoverflow.com/questions/7868991/how-can-i-reference-the-value-of-a-final-static-field-in-the-class
 *
 *
 */
@Component
public class Scrabble {

    private static final Logger logger = LoggerFactory.getLogger(Scrabble.class);

    public static final String wordFilePath = "words.txt";

    Map<Integer, List<String>> wordLengthMap;


    /**
     * Constructor. This will pull in all the words inside the file referenced by {@value wordFilePath}
     */
    public Scrabble() {
        List<String> wordList = readWordsFromFile( wordFilePath );
        this.wordLengthMap = sortWordsByLength( wordList );
    }

    public String scrabble(String inputChars){
        Map<Character, Integer> charCountMap = breakWordIntoCharCountMap( inputChars );
        boolean noWordFound = true;
        Integer longestPossibleWord = inputChars.length();

        //Iterate through the map of word lengths looking for the longest one that matches
        for (int wordLengthToCheck = longestPossibleWord; wordLengthToCheck > 0 ; wordLengthToCheck--) {
            if( wordLengthMap.containsKey( wordLengthToCheck ) ) {
                List<String> largestWords = wordLengthMap.get(wordLengthToCheck);
                for (String word : largestWords) {
                    if (checkWordAgainstCharacters(word, charCountMap)) {
                        logger.info( String.format("The word [%s] was found to match the input chars", word) );
                        return word;
                    }
                }
                logger.info( String.format("No words of length %d matched", wordLengthToCheck ) );
            } else { //No words of this length in the dictionary
                logger.info( String.format("No words of length %d were found", wordLengthToCheck ) );
            }

        }

        return ""; //TODO: determine no word edge case. For now, just empty String.
    }

    /**
     * Constructor helper method used to read words from a text file and put them into a list
     *
     * @param fileNameInResources
     * @returnsd
     */
    private List<String> readWordsFromFile(String fileNameInResources){
        List<String> wordList;
        URL fileURL = getClass().getClassLoader().getResource( wordFilePath );
        try( Stream<String> lines = Files.lines(Paths.get(fileURL.toURI())) ) {
            wordList = lines.collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            logger.error("Application errored out on Word Imports", e);
            throw new RuntimeException("Application errored out on Word Imports", e);
        }
        return wordList;
    }

    /**
     * Helper method for the constructor to take a list of words and output a length sorted map of words
     *
     * @param wordList
     * @return
     */
    private Map<Integer, List<String>> sortWordsByLength(List<String> wordList){
        Map<Integer, List<String>> wordLengthMap = new ConcurrentHashMap<>();
        wordList.forEach( (word) -> {
            boolean firstWordOfThisLength = !wordLengthMap.containsKey( word.length() );
            if( firstWordOfThisLength ){
                List<String> initWordListForThisLength = new LinkedList<>();
                initWordListForThisLength.add( word );
                wordLengthMap.put( word.length(), initWordListForThisLength );
            } else {
                List<String> existingWordList = wordLengthMap.get( word.length() );
                existingWordList.add( word );
            }
        });

        return wordLengthMap;
    }

    private boolean checkWordAgainstCharacters(String word, Map<Character, Integer> inputChars){

        for (int positionInWord = 0; positionInWord < word.length() ; positionInWord++) {
            Character charToCheck = word.charAt( positionInWord );

            if (!inputChars.containsKey( charToCheck )){
                return false;
            }
            Integer characterCount = inputChars.get( charToCheck );
            characterCount--;

            if(characterCount < 0){
                return false;
            }
        }

        return true;
    }

    /**
     * Helper method used to break the input character string of {@link #scrabble(String inputChars)} into its component
     *  character occurrences to help with analyzing other words.
     * @param charString
     * @return
     */
    private Map<Character, Integer> breakWordIntoCharCountMap(String charString){
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (int i = 0; i < charString.length() ; i++) {
            Character character = charString.charAt(i);
            boolean firstInstanceOfChar = !charCountMap.containsKey( character );
            if ( firstInstanceOfChar ){
                charCountMap.put(character, 1);
            } else {//Increment the number of occurrences by 1
                Integer charCount = charCountMap.get( character );
                charCount = charCount + 1;
            }
        }

        return charCountMap;
    }




}
