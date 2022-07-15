package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two integers dividend and divisor, divide two integers without using multiplication, division, and mod operator.
 *
 * The integer division should truncate toward zero, which means losing its fractional part. For example, 8.345 would be truncated to 8, and -2.7335 would be truncated to -2.
 *
 * Return the quotient after dividing dividend by divisor.
 *
 * Note: Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231, 231 − 1]. For this problem, if the quotient is strictly greater than 231 - 1, then return 231 - 1, and if the quotient is strictly less than -231, then return -231.
 */
public class Divide {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {101, 2, 50},
//                {-2_147_483_648, -1, 2_147_483_647},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int dividend, int divisor, int expected) {
//        System.out.println(Integer.toBinaryString(0));
//        System.out.println(Integer.toBinaryString(-0));
//        System.out.println(Integer.toBinaryString(1));
//        System.out.println(Integer.toBinaryString(-1));
//        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));

//        System.out.println(Integer.parseInt("00000000000000000000000000000000", 2));
        System.out.println(Integer.parseInt("10010000000000000000000000000000", 2));
//        assertEquals(divide(dividend, divisor), expected);
    }

//    public int divide(int dividend, int divisor) {
//        if (dividend == Integer.MIN_VALUE && divisor == -1) {
//            return Integer.MAX_VALUE;
//        }
//        return dividend / divisor;
//    }

    public int divide(int dividend, int divisor) {
        if(dividend == Integer.MIN_VALUE && divisor == -1) {return Integer.MAX_VALUE;};
        boolean sign = (dividend < 0) == (divisor < 0);
        dividend = dividend > 0? -dividend: dividend;
        divisor = divisor > 0? -divisor: divisor;
        int res = divideHelper(dividend, divisor);
        return sign? res: -res;
    }

    public int divideHelper(int dividend, int divisor){
        if(divisor < dividend) return 0;
        int sum = divisor, m = 1;
        while((Integer.MIN_VALUE - sum < sum) && (sum + sum > dividend)){
            sum += sum;
            m += m;
        }
        return m + divideHelper(dividend - sum, divisor);
    }

}
