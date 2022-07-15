/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a string path, which is an absolute path (starting with a slash '/') to a file or directory in a Unix-style file system, convert it to the simplified canonical path.
 * <p>
 * In a Unix-style file system, a period '.' refers to the current directory, a double period '..' refers to the directory up a level, and any multiple consecutive slashes (i.e. '//') are treated as a single slash '/'. For this problem, any other format of periods such as '...' are treated as file/directory names.
 * <p>
 * The canonical path should have the following format:
 * <p>
 * The path starts with a single slash '/'.
 * Any two directories are separated by a single slash '/'.
 * The path does not end with a trailing '/'.
 * The path only contains the directories on the path from the root directory to the target file or directory (i.e., no period '.' or double period '..')
 * Return the simplified canonical path.
 */
public class SimplifyPath {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"/home/", "/home"},
                {"/../", "/"},
                {"/home//foo/", "/home/foo"},
                {"/a/../../b/../c//.//", "/c"},
                {"/a/./b/../../c/", "/c"},
                {"/home/../../..", "/"},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String path, String expected) {
        assertEquals(simplifyPath(path), expected);
    }
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c != '/') {
                sb.append(c);
                continue;
            }
            process(stack, sb);
            sb.setLength(0);
        }
        process(stack, sb);
        if (stack.isEmpty()) {
            return "/";
        }
        sb.setLength(0);
        while(!stack.isEmpty()) {
            sb.append('/').append(stack.pollLast());
        }
        return sb.toString();
    }

    private void process(final Deque<String> stack, final StringBuilder currentPart) {
        String part = currentPart.toString();
        if (part.isEmpty() || part.equals(".")) {
            return;
        }
        if (part.equals("..")) {
            stack.poll();
        } else {
            stack.push(part);
        }
    }

}
