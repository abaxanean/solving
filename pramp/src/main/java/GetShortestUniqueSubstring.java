import java.util.*;
import java.util.stream.IntStream;

public class GetShortestUniqueSubstring {


    static String getShortestUniqueSubstring(char[] arr, String str) {
        if (str.length() < arr.length) {
            return "";
        }
        final Map<Character, Integer> counts = computeCounts(str);
        final Set<Character> chars = new HashSet<>();
        for (char c : arr) {
            chars.add(c);
        }
        if (!counts.keySet().containsAll(chars)) {
            return "";
        }
        return null;//shortenRecursively(str);
    }

//    private static String shortenRecursively(String str, Map<Character, Integer> counts, int start, int end) {
//        int i= 0, j;
//    }

    static Map<Character, Integer> computeCounts(String str) {
        final Map<Character, Integer> result = new HashMap<>();
        str.chars().forEach((int c) -> result.compute((char) c, (key, oldValue) -> oldValue == null ? 1 : oldValue + 1));
        return result;
    }

    public static void main(String[] args) {
        Set<Character> chars = new HashSet<>(Arrays.asList('o', 'r', 'k'));
        final String str = getShortestUniqueSubstring3(new char[]{'t', 'i', 's', 't'}, "this is a test string");
//        final String str = getShortestUniqueSubstring2(chars, "geeksforgeeks");
        System.out.println(str);
    }

    static String getShortestUniqueSubstring2(Set<Character> arr, String str) {
        int[] strCount = new int[256];

        int length = findEnd(arr, strCount, str);
        if (length == -1) {
            return "";
        }
        int start = getNewStart(arr, str, strCount, 0);
        int minLength = length - start;
        for (int i = length; i < str.length(); i++) {
            char c = str.charAt(i);
             strCount[c]++;
            if (arr.contains(c)) {
                int newStart = getNewStart(arr, str, strCount, start);
                if (i - newStart + 1 < minLength) {
                    start = newStart;
                    minLength = i - newStart + 1;
                }
            }
        }
        return str.substring(start, start + minLength);
    }

    private static int getNewStart(Set<Character> arr, String str, int[] strCount, int newStart) {
        while (!arr.contains(str.charAt(newStart)) || strCount[str.charAt(newStart)] > 1) {
            strCount[str.charAt(newStart)]--;
            newStart++;
        }
        return newStart;
    }

    private static int findEnd(Set<Character> arr, int[] strCount, String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            strCount[c]++;
            if (arr.contains(c) && strCount[c] == 1) {
                count++;
            }
            if (count == arr.size()) {
                return i + 1;
            }
        }
        return -1;
    }

    static String getShortestUniqueSubstring3(char[] arr, String str) {
        int[] arrCount = new int[256];
        int[] strCount = new int[256];
        for (char c : arr) {
            arrCount[c]++;
        }

        int length = findEnd2(arr, arrCount, strCount, str);
        if (length == -1) {
            return "";
        }
        int start = getNewStart(str, arrCount, strCount, 0);
        int minLength = length - start;
        for (int i = length; i < str.length(); i++) {
            char c = str.charAt(i);
            strCount[c]++;
            if (arrCount[c] > 0) {
                int newStart = getNewStart(str, arrCount, strCount, start);
                if (i - newStart + 1 < minLength) {
                    start = newStart;
                    minLength = i - newStart + 1;
                }
            }
        }
        return str.substring(start, start + minLength);
    }

    private static int getNewStart(String str, int[] arrCount, int[] strCount, int start) {
        while (arrCount[str.charAt(start)] == 0 || strCount[str.charAt(start)] > arrCount[str.charAt(start)]) {
            strCount[str.charAt(start)]--;
            start++;
        }
        return start;
    }

    private static int findEnd2(char[] arr, int[] arrCount, int[] strCount, String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (strCount[c] < arrCount[c]) {
                count++;
            }
            strCount[c]++;
            if (count == arr.length) {
                return i + 1;
            }
        }
        return -1;
    }
}
