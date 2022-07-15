/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EncryptedWords {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"abc", "bac"},
                {"abcd", "bacd"},
                {"abcxcba", "xbacbca"},
                {"facebook", "eafcobok"},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, String expected) {
        assertEquals(findEncryptedWord(s), expected);
    }

    String findEncryptedWord(String s) {
        StringBuilder sb = new StringBuilder();
        encrypt(s, 0, s.length() - 1, sb);
        return sb.toString();
    }

    private void encrypt(final String s, int start, int end, final StringBuilder sb) {
        if (end - start < 2) {
            for (int i = start; i <= end; i++) {
                sb.append(s.charAt(i));
            }
            return;
        }
        int middle = (start + end) / 2;
        sb.append(s.charAt(middle));
        encrypt(s, start, middle-1, sb);
        encrypt(s, middle + 1, end, sb);
    }

}
