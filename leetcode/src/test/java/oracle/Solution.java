package oracle;

import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Phone screen
 * 1. Given a list of start-end time pairs representing meetings figure out if they can all take place in a room.
 * 2. Given same input, figure out the maximum number of rooms needed
 *
 * Virtual on-site, 3 coding, 1 system, 1 talk
 * Everybody was very nice.
 * 1. Reconstruct tree from given pre-order array. The array is without nulls so you cannot use 2n+1/2n+2 approach
 * 2. Given a meeting room, check whether a meeting can be booked at a given time. The implementation is different from the phone screen
 *    because here you have to store the meeting info and use it for subsequent calls.
 * 3. System design.
 * 4. a) Given a list of tasks that can depend on other tasks, order them (topological sorting)
 *    b) Given a binary number with wildcards(e.g. 10x1xx01), list all possible numbers.
 * 5. Non-technical discussion with manager.
 */

public class Solution {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"1100xx01", null}
        };
    }

    @Test(dataProvider = "testCases")
    public void solve(String pattern, Set<String> expected) {
        printString(pattern);
    }

    private static void printString(String str) {
        printStrings(new StringBuilder(), 0, str);
    }

    private static void printStrings2(String sb, int index, String str) {
        if (index == str.length()) {
            System.out.println(sb);
            return;
        }
        char c = str.charAt(index);
        if (c != 'x') {
            printStrings2(sb + c, index + 1, str);
        } else {
            printStrings2(sb + '0', index + 1, str);
            printStrings2(sb + '1', index + 1, str);
        }

    }

    private static void printStrings(StringBuilder sb, int index, String str) {
        if (index == str.length()) {
            System.out.println(sb.toString());
            return;
        }
        char c = str.charAt(index);
        if (c != 'x') {
            sb.append(c);
            printStrings(sb, index + 1, str);
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.append('0');
            printStrings(sb, index + 1, str);
            sb.deleteCharAt(sb.length() - 1);
            sb.append('1');
            printStrings(sb, index + 1, str);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
