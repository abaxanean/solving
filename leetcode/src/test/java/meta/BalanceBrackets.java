/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.ArrayDeque;
import java.util.Deque;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * A bracket is any of the following characters: (, ), {, }, [, or ].
 * We consider two brackets to be matching if the first bracket is an open-bracket, e.g., (, {, or [, and the second bracket is a close-bracket of the same type. That means ( and ), [ and ], and { and } are the only pairs of matching brackets.
 * Furthermore, a sequence of brackets is said to be balanced if the following conditions are met:
 * The sequence is empty, or
 * The sequence is composed of two or more non-empty sequences, all of which are balanced, or
 * The first and last brackets of the sequence are matching, and the portion of the sequence without the first and last elements is balanced.
 * You are given a string of brackets. Your task is to determine whether each sequence of brackets is balanced. If a string is balanced, return true, otherwise, return false
 */
public class BalanceBrackets {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"{[(])}", false},
                {"{{[[(())]]}}", true},
                {"{[()]}", true},
                {"{}()", true},
                {"{(})", false},
                {")", false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, boolean expected) {
        assertEquals(isBalanced(s), expected);
    }

    boolean isBalanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '{':
                    stack.push('}');break;
                case '(':
                    stack.push(')');break;
                case '[':
                    stack.push(']');break;
                default:
                    if (stack.isEmpty() || c != stack.pop()) {
                        return false;
                    }
            }
        }
        return stack.isEmpty();
    }
}
