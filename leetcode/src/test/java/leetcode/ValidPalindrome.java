/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ValidPalindrome {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"A man, a plan, a canal: Panama", true},
                {"race a car", false},
                {" ", true},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, boolean expected) {
        assertEquals(isPalindrome(s), expected);
    }


    public boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;
        while (start < end) {
            char sChar = lowerCase(s.charAt(start));
            char eChar = lowerCase(s.charAt(end));
            if (!isAlphaNumeric(sChar)) {
                start++;
                continue;
            }
            if (!isAlphaNumeric(eChar)) {
                end--;
                continue;
            }
            if (sChar != eChar) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    boolean isAlphaNumeric(char c) {
        return (c >= 'a' && c <= 'z')
                || (c >= '0' && c <= '9');
    }

    int delta = 'a' - 'A';
    char lowerCase(char c) {
        if (c < 'A' || c > 'Z') {
            return c;
        }
        return (char) (c + this.delta);
    }

//    public static void main(String[] args) {
//        System.out.println((int) 'a');
//        System.out.println((int) 'A');
//        System.out.println(lowerCase('B'));
//    }
}
