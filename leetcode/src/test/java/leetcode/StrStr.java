package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Implement strStr().
 *
 * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 *
 * Clarification:
 *
 * What should we return when needle is an empty string? This is a great question to ask during an interview.
 *
 * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
 */
public class StrStr {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"hello", "ll", 2},
                {"", "", 0},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String haystack, String needle, int expected) {
        assertEquals(strStr(haystack, needle), expected);
    }

    public int strStr(String haystack, String needle) {
        haystack.indexOf(needle);
        if (needle.isEmpty()) {
            return 0;
        }

        char first = needle.charAt(0);
        int max = haystack.length() - needle.length();

        for (int i =0; i <= max; i++) {
            /* Look for first character. */
            if (haystack.charAt(i) != first) {
                while (++i <= max && haystack.charAt(i) != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + needle.length() - 1;
                for (int k = 1; j < end && haystack.charAt(j) == needle.charAt(k); j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i;
                }
            }
        }
        return -1;
    }
}
