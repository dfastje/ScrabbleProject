package me.dfastje.ScrabbleProject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class ScrabbleBruteForceServiceTest {

    @Autowired
    private ScrabbleBruteForceService scrabbleBruteForceService;

    static class ScrabbleProvider{
        @Bean
        public ScrabbleBruteForceService scrabbleProvider(){
            return new ScrabbleBruteForceService();
        }
    }

    @Test
    void scrabble_SanityCheck() {
        Assertions.assertEquals("a", scrabbleBruteForceService.scrabble("a"));
    }

    @Test
    void scrabble_RequirementsCheck() {
        Assertions.assertEquals("canary", scrabbleBruteForceService.scrabble("rancary"));
        Assertions.assertEquals("carry", scrabbleBruteForceService.scrabble("rancry"));

    }

    @Test
    void scrabble_CapitalizationCheck() {
        Assertions.assertEquals("", scrabbleBruteForceService.scrabble("A"));
    }

    @Test
    void scrabble_NoMatchingWord() {
        Assertions.assertEquals("", scrabbleBruteForceService.scrabble("zzzz"));
    }

    @Test
    void scrabble_LongWordTest() {
        Assertions.assertEquals("recommendation", scrabbleBruteForceService.scrabble("recommendation"));
    }

    @Test
    void scrabble_InputCharsLongerThanLongestWord() {
        Assertions.assertNotNull( scrabbleBruteForceService.scrabble("recommendationrecommendation") );
    }

    @Test
    void scrabble_CharacterCountCheck() {
        //If Characters are only being checked for existence, "falsee" will return assess
        Assertions.assertEquals("false", scrabbleBruteForceService.scrabble("falsee"));
    }



}