/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

public class CouldBeAnagram {

    public static void main(String[] args) {
        boolean result = new CouldBeAnagram().couldBeAnagram("djkcnrugymas?", "kdjncgurmysa");
        System.out.println(result);
    }

    boolean couldBeAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        // count occurrences of all ASCII lower-case chars and question marks
        int[] s1Chars = new int[26];
        int[] s2Chars = new int[26];
        int questionMarks = 0;
        for (int i = 0; i < s1.length(); i++) {
            incrementCount(i, s2, s2Chars);
            if (s1.charAt(i) == '?') {
                questionMarks++;
            } else {
                incrementCount(i, s1, s1Chars);
            }
        }
        for (int i = 0; i < 26; i++) {
            // how many more occurrences of a specific char there are in s1 compared to s2
            int diff = s1Chars[i] - s2Chars[i];
            if (diff == 0) {
                // same number of occurrences
                continue;
            }
            // if a char occurs more times in s1 than in s2 OR
            // if it occurs more times in s2 AND we don't have enough question marks
            if (diff > 0 || questionMarks < -diff) {
                return false;
            }
            // reduce the available question marks (arriving here means diff <= 0 and there are enough question marks)
            questionMarks += diff;
        }
        return true;
    }

    // int offset = (int)'a' = 97
    void incrementCount(int index, String s, int[] array) {
        array[s.charAt(index) - 97] += 1;
    }
}