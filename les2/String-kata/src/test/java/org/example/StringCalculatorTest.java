package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {
    private StringCalculator stringCalculator;

    @BeforeEach
    void setup() {
       stringCalculator = new StringCalculator();
    }

    @Test
    void testEmptyString() {
        assertEquals(0, stringCalculator.add(""));
    }

    @Test
    void testSingleNumber() {
        assertEquals(1, stringCalculator.add("1"));
    }

    @Test
    void testNewLineNumber() {
        assertEquals(6, stringCalculator.add("1\n2,3"));
    }

    @Test
    void testDelimiterNumber() {
        assertEquals(3, stringCalculator.add("//;\n1;2"));
    }

    @Test
    void testLargerThenThousand() {
        assertEquals(2, stringCalculator.add("2 ,1001"));
    }

    @Test
    void testAssortedDelimiters() {
        assertEquals(6, stringCalculator.add("//[***]\n1***2***3"));
    }
}
