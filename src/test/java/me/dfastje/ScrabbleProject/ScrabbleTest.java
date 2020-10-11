package me.dfastje.ScrabbleProject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScrabbleTest {

    @Autowired
    private Scrabble scrabble;

    static class ScrabbleProvider{
        @Bean
        public Scrabble scrabbleProvider(){
            return new Scrabble();
        }
    }

    @Test
    void scrabble_SanityCheck() {
        Assertions.assertEquals("a", scrabble.scrabble("a"));
    }

    @Test
    void scrabble_NoMatchingWord() {
        Assertions.assertEquals("", scrabble.scrabble("zzzz"));
    }

    @Test
    void scrabble_LongWordTest() {
        Assertions.assertEquals("recommendation", scrabble.scrabble("recommendation"));
    }

    @Test
    void scrabble_InputCharsLongerThanLongestWord() {
        Assertions.assertNotNull( scrabble.scrabble("recommendationrecommendation") );
    }

    @Test
    void scrabble_RandomWordTest() {
        Assertions.assertEquals("false", scrabble.scrabble("falsee"));
    }

}