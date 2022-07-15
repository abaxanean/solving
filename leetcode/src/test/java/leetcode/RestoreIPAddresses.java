/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;

import static leetcode.shared.ListNode.createList;
import static org.testng.Assert.assertEquals;

/**
 * A valid IP address consists of exactly four integers separated by single dots. Each integer is between 0 and 255 (inclusive) and cannot have leading zeros.
 *
 * For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses, but "0.011.255.245", "192.168.1.312" and "192.168@1.1" are invalid IP addresses.
 * Given a string s containing only digits, return all possible valid IP addresses that can be formed by inserting dots into s. You are not allowed to reorder or remove any digits in s. You may return the valid IP addresses in any order.
 */
public class RestoreIPAddresses {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"25525511135", Arrays.asList("255.255.11.135","255.255.111.35")},
                {"0000", Arrays.asList("0.0.0.0")},
                {"101023", Arrays.asList("1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3")},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, List<String> expected) {
        this.result.clear();
        assertEquals(restoreIpAddresses(s), expected);
    }

    List<String> result = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        if (s.length() > 16 || s.length() < 4) {
            return Collections.emptyList();
        }
        int[] numbers = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            numbers[i] = s.charAt(i) - '0';
        }
        restoreIpAddresses(numbers, new int[4], 0, 0);
        return result;
    }

    private void restoreIpAddresses(int[] s, int[] ip, int index, int part) {
        // check if we still can consume the remaining numbers
        if (s.length - index > (4 - part) * 3) {
            return;
        }

        if (part == 4) { // if we have 4 valid integers
            result.add(toIp(ip));
            return;
        }

        int number = 0;
        for(int i = index; i < Math.min(s.length, index + 3); i++) {
            number *= 10;
            number += s[i];
            if (number <= 255) {
                ip[part] = number;
                restoreIpAddresses(s, ip, i+1, part + 1);
            }
            if (number == 0) {
                break;
            }
        }
    }

    private String toIp(int[] ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip[0]);
        for(int i = 1; i < 4; i++) {
            sb.append('.').append(ip[i]);
        }
        return sb.toString();
    }
}
