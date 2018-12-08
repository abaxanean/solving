/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReorderLines {

    public static void main(String[] args) {
        List<String> result = new ReorderLines().reorderLines(4, Arrays.asList(
                "ab3 jog mid pet",
                "wz3 34 54 398",
                "a1 alps cow bar",
                "abc jog mid pet"
        ));
        System.out.println(result);
    }

    public List<String> reorderLines(int logFileSize, List<String> logLines) {
        if (logFileSize == 0) {
            return Collections.emptyList();
        }
        // use list instead of TreeMap to preserve duplicated lines
        List<Key> wordLines = new ArrayList<>();
        List<String> numberLines = new ArrayList<>();
        for (String line : logLines) {
            // space-delimited list of strings, so split by space
            String[] parts = line.split(" ", 2);
            String identifier = parts[0];
            String remainder = parts[1];
            char c = remainder.charAt(0);
            if (Character.isLowerCase(c)) {
                wordLines.add(new Key(line, remainder, identifier));
            } else {
                numberLines.add(line);
            }

        }
        // sorted lines with words first
        wordLines.sort(null);
        List<String> result = wordLines.stream()
                .map(Key::getLine)
                .collect(Collectors.toList());
        // than add lines with numbers
        result.addAll(numberLines);
        return result;
    }

    static class Key implements Comparable<Key> {

        private final String line;
        private final String remainder;
        private final String identifier;

        Key(final String line, final String remainder, final String identifier) {
            this.line = line;
            this.remainder = remainder;
            this.identifier = identifier;
        }

        String getLine() {
            return line;
        }

        @Override
        public int compareTo(final Key o) {
            // compare the line ignoring the identifier
            int result = this.remainder.compareTo(o.remainder);
            if (result != 0) {
                return result;
            }
            // in case of a tie, compare the identifier
            return this.identifier.compareTo(o.identifier);
        }
    }
}
