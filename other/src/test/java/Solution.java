import java.util.stream.IntStream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * TODO: Document this class.
 */
public class Solution {

    @DataProvider(name = "testCases")
    public static Object[][] testCases() {
        return IntStream.range(0, 1000)
                .mapToObj(i -> new Object[]{i})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "testCases")
    public void test(int i) {
        System.out.println(i);
    }
}
