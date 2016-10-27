/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=22
 * <pre>
 * Using names.txt (right click and 'Save Link/Target As...'), a 46K text file containing over five-thousand first names,
 * begin by sorting it into alphabetical order. Then working out the alphabetical value for each name,
 * multiply this value by its alphabetical position in the list to obtain a name score.
 * For example, when the list is sorted into alphabetical order, COLIN, which is worth 3 + 15 + 12 + 9 + 14 = 53,
 * is the 938th name in the list. So, COLIN would obtain a score of 938 × 53 = 49714.
 * What is the total of all the name scores in the file?
 * </pre>
 */
public class P022_NamesScores {

    @Test
    public void solve() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getResource("/p022_names.txt").toURI());
        String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        String[] names = text.replace("\"", "").split(",");
        Arrays.sort(names);
        int i = 1;
        int result = 0;
        for (String name : names) {
            result += i * alphabeticalValue(name);
            i++;
        }
        assertEquals(result, 871198282);
    }

    private int alphabeticalValue(final String name) {
        int result = 0;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            // A = 1. B = 2, C = 3, etc
            result += c - 'A' + 1;
        }
        return result;
    }

}
