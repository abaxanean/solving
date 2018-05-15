package com.bax.codefights;

import java.util.stream.IntStream;

/**
 * <pre>
 * You're running a "Buy 2, Get 1 Free" promotion at your store. Your customers can group any 3 consecutive items in their shopping cart, and the cheapest item from every such group of 3 items will be free.
 *
 * Given a list of prices, your task is to maximize the discount your customer can get.
 *
 * Example
 * For prices = [10, 20, 17, 7, 16, 19, 16], the output should be
 * maxDiscount(prices) = 26.
 * The customer can group their items as follows: (10, 20, 17), 7, (16,19,16). Thus, they can get a $10 discount from the first group and a $16 discount from the second group, for a total discount of $26.
 * For prices = [1, 2, 7, 8, 10, 2], the output should be
 * maxDiscount(prices) = 7.
 * The customer can group their items as follows: 1, 2, (7, 8, 10), 2. Thus, they can get a $7 discount.
 * </pre>
 */
public class MaxDiscount {

    public static void main(String[] args) {
        System.out.println(new MaxDiscount().maxDiscount(new int[]{2, 2, 7, 8, 10, 6}));
    }

    int maxDiscount(int[] prices) {
//        int
        return 0;
    }

//    int maxDiscount3(int[] prices) {
//        int discount = 0;
//        int first = 0, second = 0, third = 0;
//        for (int i = 0; i < prices.length - 2; i++) {
//            first = second;
//            second = third;
//            third = discount;
//            int min = min(prices[i], prices[i + 1], prices[i + 2]);
//            first += min;
//            discount = Math.max(discount, first);
//        }
//        return discount;
//    }

    private int min(int... i) {
        return IntStream.of(i).min().orElse(0);
    }

    //simple dynamic programming problem, with a simple improve and no array needed
//    int a, b, c, d, x, y;
//    //init all value to 0, which is not necessary to type cause it's done by default in initialization
//    int maxDiscount2(int[] prices) {
//
//        //x, y and z are 3 last number which is being processed in the array
//        //a, b, c are most optimized  values so far
//        for(int z : prices){
//            a = b;
//            b = c;
//            d = Math.max(c = d, a += Math.min(x, Math.min(x=y,y=z)));
//            // d = max(d, a + min(x, y, z)) modification of a in above line wont affect anything as a will be changed again in the next iteration
//        }
//        return d;
//    }

}
