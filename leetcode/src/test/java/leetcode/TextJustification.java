/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of strings words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.
 * <p>
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.
 * <p>
 * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line does not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
 * <p>
 * For the last line of text, it should be left-justified and no extra space is inserted between words.
 * <p>
 * Note:
 * <p>
 * A word is defined as a character sequence consisting of non-space characters only.
 * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
 * The input array words contains at least one word.
 */
public class TextJustification {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16, Arrays.asList("This    is    an", "example  of text", "justification.  ")},
                {new String[]{"What", "must", "be", "acknowledgment", "shall", "be"}, 16, Arrays.asList("What   must   be", "acknowledgment  ", "shall be        ")},
                {new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"}, 20,
                        Arrays.asList("Science  is  what we", "understand      well", "enough to explain to", "a  computer.  Art is", "everything  else  we", "do                  ")},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String[] words, int maxWidth, List<String> expected) {
        assertEquals(fullJustify(words, maxWidth), expected);
    }

    int maxWidth;

    public List<String> fullJustify(String[] words, int maxWidth) {
        this.maxWidth = maxWidth;
        List<String> result = new ArrayList<>();
        int wordCount = 1;
        int lineLength = words[0].length();
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            if (lineLength + word.length() + 1 > maxWidth) {
                result.add(createLinePadEvenly(words, wordCount, maxWidth - lineLength, i));
                wordCount = 1;
                lineLength = word.length();
                continue;
            }
            lineLength += 1 + word.length();
            wordCount++;
        }
        // last line
        result.add(createLastLine(words, wordCount));
        return result;
    }

    String createLinePadEvenly(String[] words, int wordCount, int spacesToBeAdded, int endIndexExclusive) {
        StringBuilder sb = new StringBuilder(this.maxWidth);
        int spaceSlots = wordCount - 1;
        int startIndex = endIndexExclusive - wordCount;
        sb.append(words[startIndex]);
        for (int i = startIndex + 1; i < endIndexExclusive; i++) {
            int spaces = spacesToBeAdded / spaceSlots;
            if (spacesToBeAdded % spaceSlots > 0) {
                spaces++;
            }
            spacesToBeAdded -= spaces;
            spaceSlots--;
            // doing <= to account for the single required space, not included in spacesToBeAdded
            for (int j = 0; j <= spaces; j++) {
                sb.append(' ');
            }
            sb.append(words[i]);
        }
        // if there is only 1 word there are no space slots
        while (sb.length() < this.maxWidth) {
            sb.append(' ');
        }
        return sb.toString();
    }

    String createLastLine(String[] words, int wordCount) {
        StringBuilder sb = new StringBuilder(this.maxWidth);
        int startIndex = words.length - wordCount;
        sb.append(words[startIndex]);
        for (int i = startIndex + 1; i < words.length; i++) {
            sb.append(' ').append(words[i]);
        }
        while (sb.length() < this.maxWidth) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
