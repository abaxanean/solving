package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
 */
public class LongestValidParentheses {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"(()", 2},
                {")()())", 4},
                {"", 0},
                {"())((())", 4},
                {"))(((", 0},
                {"()(()", 2},
                {"(()", 2},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, int expected) {
        assertEquals(longestValidParentheses(s), expected);
    }

    public int longestValidParentheses(String s) {
        int maxans = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
                continue;
            }
            stack.pop();
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                maxans = Math.max(maxans, i - stack.peek());
            }

        }
        return maxans;
    }

//    public int longestValidParentheses(String s) {
//        int left = 0, right = 0, maxlength = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == '(') {
//                left++;
//            } else {
//                right++;
//            }
//            if (left == right) {
//                maxlength = Math.max(maxlength, 2 * right);
//            } else if (right >= left) {
//                left = right = 0;
//            }
//        }
//        left = right = 0;
//        for (int i = s.length() - 1; i >= 0; i--) {
//            if (s.charAt(i) == '(') {
//                left++;
//            } else {
//                right++;
//            }
//            if (left == right) {
//                maxlength = Math.max(maxlength, 2 * left);
//            } else if (left >= right) {
//                left = right = 0;
//            }
//        }
//        return maxlength;
//    }
}
