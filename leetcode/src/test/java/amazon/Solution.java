package amazon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Solution {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {1, new HashSet<>(Arrays.asList("()"))},
                {2, new HashSet<>(Arrays.asList("()()", "(())"))},
                {3, new HashSet<>(Arrays.asList("()()()", "(())()", "()(())", "(()())", "((()))"))},

        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, Set<String> expected) {
        this.collector = new HashSet<>();
        generate(new StringBuilder(n * 2), n * 2, 0);
        assertEquals(this.collector, expected);
    }

    Set<String> collector;

    private void generate(final StringBuilder current, final int charsLeft, final int open) {
        if (charsLeft == 0) {
            this.collector.add(current.toString());
            return;
        }
        if (open < charsLeft) {
            generate(current.append("("), charsLeft - 1, open + 1);
            current.setLength(current.length() - 1);
        }
        if (open > 0) {
            generate(current.append(")"), charsLeft - 1, open - 1);
            current.setLength(current.length() - 1);
        }
    }

}
