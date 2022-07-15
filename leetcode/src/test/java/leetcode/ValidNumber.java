/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * TODO: Document this class.
 */
public class ValidNumber {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"0", true},
                {"e", false},
                {".", false},
                {"2", true},
                {"0089", true},
                {"-0.1", true},
                {"+3.14", true},
                {"4.", true},
                {"-.9", true},
                {"2e10", true},
                {"-90E3", true},
                {"3e+7", true},
                {"+6e-1", true},
                {"53.5e93", true},
                {"-123.456e789", true},

                {"abc", false},
                {"1a", false},
                {"1e", false},
                {"e3", false},
                {"99e2.5", false},
                {"--6", false},
                {"-+3", false},
                {"95a54e53", false},

        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, boolean expected) {
        assertEquals(isNumber(s), expected);
    }

    public boolean isNumber(String s) {
        s = s.toLowerCase();
        int indexOfE = s.indexOf('e');
        if (indexOfE == -1) {
            return parseDecimalNumberOrInteger(s, true);
        }
        return parseDecimalNumberOrInteger(s.substring(0, indexOfE), true)
                && parseDecimalNumberOrInteger(s.substring(indexOfE + 1), false);
    }

    boolean parseDecimalNumberOrInteger(String s, boolean acceptDot) {
        if (s.length() == 0) {
            return false;
        }
        int start = s.charAt(0) == '-' || s.charAt(0) == '+' ? 1 : 0;
        boolean digit = false;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '.') {
                if (!acceptDot) {
                    return false;
                }
                acceptDot = false;
                continue;
            }
            if (c < '0' || c > '9') {
                return false;
            }
            digit = true;
        }
        return digit;
    }
}
