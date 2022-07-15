/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two binary strings a and b, return their sum as a binary string.
 */
public class AddBinary {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"11", "1", "100"},
                {"1010", "1011", "10101"},

        };
    }

    @Test(dataProvider = "testCases")
    public void test(String a, String b, String expected) {
        assertEquals(addBinary(a, b), expected);
    }

    public String addBinary(String a, String b) {
        int aIndex = a.length();
        int bIndex = b.length();
        int max = Math.max(aIndex, bIndex);
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < max; i++) {
            aIndex--; bIndex--;
            int aChar = aIndex < 0 ? 0 : (a.charAt(aIndex) - '0');
            int bChar = bIndex < 0 ? 0 : (b.charAt(bIndex) - '0');
            int result = aChar + bChar + carry;
            sb.append((result & 1));
            carry = result >> 1;
        }
        if (carry == 1) {
            sb.append(1);
        }
        return sb.reverse().toString();
    }

    public String addBinary2(String a, String b) {
        int aIndex = a.length();
        int bIndex = b.length();
        int maxLength = Math.max(aIndex, bIndex);
        int charIndex = maxLength + 1;
        int carry = 0;
        char[] chars = new char[maxLength + 1];
        for (int i = 0; i < maxLength; i++) {
            aIndex--;
            bIndex--;
            charIndex--;
            int aChar = aIndex < 0 ? 0 : (a.charAt(aIndex) - '0');
            int bChar = bIndex < 0 ? 0 : (b.charAt(bIndex) - '0');
            int result = aChar + bChar + carry;
            chars[charIndex] = (char)((result & 1) + '0');
            carry = result >> 1;
        }

        if (carry == 0) {
            return new String(chars, 1, chars.length - 1);
        }
        chars[0] = '1';
        return new String(chars);
    }
}
