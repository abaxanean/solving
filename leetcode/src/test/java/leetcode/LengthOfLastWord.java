/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a string s consisting of some words separated by some number of spaces, return the length of the last word in the string.
 * <p>
 * A word is a maximal substring consisting of non-space characters only.
 */
public class LengthOfLastWord {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"Hello World", 5},
                {"   fly me   to   the moon  ", 4},
                {"luffy is still joyboy", 6},
                {"a", 1},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, int expected) {
        assertEquals(lengthOfLastWord(s), expected);
    }

    public int lengthOfLastWord(String s) {
        int index = s.length();
        while (s.charAt(--index) == ' ') {
        }
        int wordEnd = index;
        while (index >= 0 && s.charAt(index) != ' ') {
            index--;
        }
        return wordEnd - index;
    }

}
