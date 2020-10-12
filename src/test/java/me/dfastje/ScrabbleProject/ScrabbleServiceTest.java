package me.dfastje.ScrabbleProject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class ScrabbleServiceTest {

    @Autowired
    private ScrabbleService scrabbleService;

    static class ScrabbleProvider{
        @Bean
        public ScrabbleService scrabbleProvider(){
            return new ScrabbleService();
        }
    }

    @Test
    void scrabble_SanityCheck() {
        Assertions.assertEquals("a", scrabbleService.scrabble("a"));
    }

    @Test
    void scrabble_RequirementsCheck() {
        Assertions.assertEquals("canary", scrabbleService.scrabble("rancary"));
        Assertions.assertEquals("carry", scrabbleService.scrabble("rancry"));

    }

    @Test
    void scrabble_CapitalizationCheck() {
        Assertions.assertEquals("", scrabbleService.scrabble("A"));
    }

    @Test
    void scrabble_NoMatchingWord() {
        Assertions.assertEquals("", scrabbleService.scrabble("zzzz"));
    }

    @Test
    void scrabble_LongWordTest() {
        Assertions.assertEquals("recommendation", scrabbleService.scrabble("recommendation"));
    }

    @Test
    void scrabble_InputCharsLongerThanLongestWord() {
        Assertions.assertNotNull( scrabbleService.scrabble("recommendationrecommendation") );
    }

    @Test
    void scrabble_CharacterCountCheck() {
        //If Characters are only being checked for existence, "falsee" will return assess
        Assertions.assertEquals("false", scrabbleService.scrabble("falsee"));
    }



}