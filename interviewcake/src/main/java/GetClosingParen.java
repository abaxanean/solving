/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

public class GetClosingParen {
    public static int getClosingParen(String sentence, int openingParenIndex) throws Exception {
        int opened = 1;
        int index = openingParenIndex;
        while (opened > 0 && index < sentence.length()) {
            index++;
            char c = sentence.charAt(index);
            if (c == ')') {
                opened--;
            }  else if (c == '(') {
                opened++;
            }
        }
        if (opened > 0) {
            throw new RuntimeException();
        }
        return index;
    }

    public static void main(String[] args) throws Exception {
        int result = getClosingParen("((((()))))", 2);
        System.out.println(result);
    }
}
