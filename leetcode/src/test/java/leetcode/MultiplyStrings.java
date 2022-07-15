/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.
 * Note: You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */
public class MultiplyStrings {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"2", "3", "6"},
                {"123", "456", "56088"},
                {"1", "456", "456"},
                {"456", "1", "456"},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String num1, String num2, String expected) {
        assertEquals(multiply(num1, num2), expected);
    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        for (int i = num1.length() - 1; i >= 0; i--) {
            StringBuilder iteration = new StringBuilder();
            char c1 = num1.charAt(i);
            int number1 = c1 - '0';
            int memory = 0;
            for (int j = num2.length() - 1; j >= 0; j--) {
                char c2 = num2.charAt(j);
                int number2 = c2 - '0';
                int product = number1 * number2 + memory;
                memory = product / 10;
                iteration.insert(0, (char)('0' + product % 10));
            }
            if (memory > 0) {
                iteration.insert(0, memory);
            }
            add(iteration, result, num1.length() - i - 1);
        }
        return result.toString();
    }

    private void add(final StringBuilder iteration, final StringBuilder collector, final int shift) {
        if (collector.length() == 0) {
            collector.append(iteration);
            return;
        }
        int memory = 0;
        for (int i = iteration.length() - 1, j = collector.length() - 1 - shift; i >= 0; i--, j--) {
            char c1 = iteration.charAt(i);
            int number1 = c1 - '0';
            char c2 = j >= 0 ? collector.charAt(j) : '0';
            int number2 = c2 - '0';
            int sum = number1 + number2 + memory;
            memory = sum / 10;
            char c = (char)('0' + sum % 10);
            if (j >= 0) {
                collector.setCharAt(j, c);
            } else {
                collector.insert(0, c);
            }

        }
        if (memory > 0) {
            collector.insert(0, memory);
        }
    }

}
