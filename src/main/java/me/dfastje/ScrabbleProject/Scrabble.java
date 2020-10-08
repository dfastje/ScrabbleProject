package me.dfastje.ScrabbleProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Words taken from https://www.ef.com/wwen/english-resources/english-vocabulary/top-3000-words/
 * File reading adapted from: https://mkyong.com/java/java-how-to-read-a-file-into-a-list/
 * Getting file from resources directory: https://mkyong.com/java/java-read-a-file-from-resources-folder/
 * Default springboot logging: https://www.baeldung.com/spring-boot-logging
 *
 */
@Component
public class Scrabble {

    private static final Logger logger = LoggerFactory.getLogger(Scrabble.class);

    public static final String wordFilePath = "words.txt";

    Map<Integer, List<String>> wordLengthMap;


    public Scrabble() {
        List<String> wordList = readWordsFromFile(wordFilePath);
        this.wordLengthMap = sortWordsByLength(wordList);
    }

    public String scrabble(String inputChars){


        return inputChars; //TODO: write method
    }

    /**
     * Constructor helper method used to read words from a text file and put them into a list
     *
     * @param fileNameInResources
     * @return
     */
    private List<String> readWordsFromFile(String fileNameInResources){
        List<String> wordList;
        URL fileURL = getClass().getClassLoader().getResource(wordFilePath);
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

}
