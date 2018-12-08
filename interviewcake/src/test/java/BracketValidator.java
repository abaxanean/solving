/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BracketValidator {

    public static boolean isValid(String code) {
        final Deque<Character> queue = new ArrayDeque<>();

        for (char c : code.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                queue.addFirst(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (queue.isEmpty()) {
                    return false;
                }
                char lastOpener = queue.removeFirst();
                if (lastOpener == '(' && c != ')'
                        || lastOpener == '[' && c != ']'
                        || lastOpener == '{' && c != '}') {
                    return false;
                }
            }
        }
        return queue.isEmpty();
    }

    @Test
    public void validShortCodeTest() {
        final boolean result = isValid("()");
        assertTrue(result);
    }

    @Test
    public void validLongerCodeTest() {
        final boolean result = isValid("([]{[]})[]{{}()}");
        assertTrue(result);
    }

    @Test
    public void mismatchedOpenerAndCloserTest() {
        final boolean result = isValid("([][]}");
        assertFalse(result);
    }

    @Test
    public void interleavedOpenersAndClosersTest() {
        final boolean result = isValid("([)]");
        assertFalse(result);
    }

    @Test
    public void missingCloserTest() {
        final boolean result = isValid("[[]()");
        assertFalse(result);
    }

    @Test
    public void extraCloserTest() {
        final boolean result = isValid("[[]]())");
        assertFalse(result);
    }

    @Test
    public void emptyStringTest() {
        final boolean result = isValid("");
        assertTrue(result);
    }

}
