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

import org.testng.annotations.Test;

import com.bax.euler.util.Triangle;

import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=67
 * <pre>
 * By starting at the top of the triangle below and moving to adjacent numbers on the row below,
 * the maximum total from top to bottom is 23.
 *    3
 *   7 4
 *  2 4 6
 * 8 5 9 3
 * That is, 3 + 7 + 4 + 9 = 23.
 * </pre>
 * Find the maximum total from top to bottom in triangle.txt (right click and 'Save Link/Target As...'),
 * a 15K text file containing a triangle with one-hundred rows.
 */
public class P067_MaximumPathSumII extends Triangle {

    @Test
    public void solve() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getResource("/p067_triangle.txt").toURI());
        String triangle = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        assertEquals(computeMaximum(triangle), 7273);
    }

}
