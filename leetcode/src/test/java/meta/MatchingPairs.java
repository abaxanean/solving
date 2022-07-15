/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MatchingPairs {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"abcde", "adcbe", 5},
                {"abcd", "abcd", 2},
                {"abca", "abca", 4},
                {"abcd", "abce", 2},
                {"abca", "abce", 3},
                {"abcd", "aefd", 2},
                {"abcd", "abec", 3},
                {"abcdefg", "afpbcbg", 4},
        };
    }


    @Test(dataProvider = "testCases")
    public void test(String s, String t, int expected) {
        assertEquals(matchingPairs(s, t), expected);
    }


    int matchingPairs(String s, String t) {
        // keep track of all chars in s to figure out whether there are duplicates
        // swapping two identical items allows us to preserve the number of matches
        Set<Character> sChars = new HashSet<>();
        boolean hasDuplicates = false;
        // the sets of chars that did not match in the two strings
        Set<Character> sCharsNoMatch = new HashSet<>();
        Set<Character> tCharsNoMatch = new HashSet<>();
        // when the chars at an index don't match, add them to this set concatenated
        Set<String> pairsThatDontMatch = new HashSet<>();
        boolean foundInvertedPair = false;
        int diffCount = 0;
        for (int i = 0; i < s.length(); i++) {
            final char sChar = s.charAt(i);
            final char tChar = t.charAt(i);
            if (!hasDuplicates) {
                hasDuplicates = !sChars.add(sChar);
            }
            if (sChar == tChar) {
                continue;
            }
            // if they are different
            diffCount++;
            String pair = "" + sChar + tChar;
            if (!foundInvertedPair) {
                pairsThatDontMatch.add(pair);
                foundInvertedPair = pairsThatDontMatch.contains("" + tChar + sChar);
            }
            sCharsNoMatch.add(sChar);
            tCharsNoMatch.add(tChar);
        }
        // match count before swapping
        int matchCount = s.length() - diffCount;
        if (diffCount < 2) {
            return hasDuplicates ? matchCount : s.length() - 2;
        }

        if (foundInvertedPair) {
            return matchCount + 2;
        }
        sCharsNoMatch.retainAll(tCharsNoMatch);
        return sCharsNoMatch.isEmpty() ? matchCount : matchCount + 1;
    }

}
