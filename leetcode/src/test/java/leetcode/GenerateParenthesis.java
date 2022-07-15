package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 */
public class GenerateParenthesis {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {1, Collections.singletonList("()")},
                {2, Arrays.asList("(())", "()()")},
                {3, Arrays.asList("((()))", "(()())", "(())()", "()(())", "()()()")},
                {4, Arrays.asList("(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())", "(()())()", "(())(())", "(())()()", "()((()))", "()(()())", "()(())()", "()()(())", "()()()()")}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, List<String> expected) {
        assertEquals(new HashSet<>(generateParenthesis(n)), new HashSet<>(expected));
//        System.out.println("Iterations " + i);
    }

    public List<String> generateParenthesis(int n) {
        final List<String> result = new ArrayList<>();
        // every result has n * 2 characters
        final char[] chars = new char[n * 2];
        recursive(chars.length - 1, 0, chars, result);
        return result;
    }

    public void recursive(int n, int closedCount, char[] chars, List<String> collector) {
        // closedCount < 0 means not all open parenthesis are closed
        // closedCount > n + 1 means we have more closed parenthesis than chars left
        if (closedCount < 0 || closedCount > n + 1) {
            return;
        }
        chars[n] = ')';
        recursive(n - 1, closedCount + 1, chars, collector);
        chars[n] = '(';
        if (n == 0 && closedCount == 1) {
            collector.add(new String(chars));
            return;
        }
        recursive(n - 1, closedCount - 1, chars, collector);
    }
}
