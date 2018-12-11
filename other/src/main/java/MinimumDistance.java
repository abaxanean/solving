/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

public class MinimumDistance {

    public static void main(String[] args) {
        System.out.println(new MinimumDistance().minimumDistance(5, 5, Arrays.asList(
                Arrays.asList(1, 1, 1, 1, 1),
                Arrays.asList(0, 0, 0, 0, 1),
                Arrays.asList(1, 1, 1, 1, 1),
                Arrays.asList(1, 0, 0, 0, 0),
                Arrays.asList(1, 1, 1, 1, 9)
        )));
        System.out.println(size);
    }

    boolean[][] visited;
    static int size = 0;

    int minimumDistance(int numRows, int numColumns, List<List<Integer>> area) {
        this.visited = new boolean[numRows][numColumns];
        final Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0, 0});
        this.visited[0][0] = true;
        while (!queue.isEmpty()) {
            int[] coords = queue.removeFirst();
            int x = coords[0];
            int y = coords[1];
            int cost = coords[2];
            if (area.get(x).get(y) == 9) {
                return cost;
            }
            addIfValid(queue, area, x - 1, y, cost + 1);
            addIfValid(queue, area, x + 1, y, cost + 1);
            addIfValid(queue, area, x, y - 1, cost + 1);
            addIfValid(queue, area, x, y + 1, cost + 1);
            if (queue.size() > size) {
                size = queue.size();
            }
        }
        return -1;
//        return recursiveSearch(area, 0, 0, 0);
    }

    private void addIfValid(Deque<int[]> queue, List<List<Integer>> area, int x, int y, int cost) {
        if (x < 0 || y < 0 || x >= area.size() || y >= area.get(x).size()) {
            return;
        }
        if (area.get(x).get(y) == 0 || visited[x][y]) {
            return;
        }
        visited[x][y] = true;
        queue.addLast(new int[]{x, y, cost});
    }

//    int recursiveSearch(List<List<Integer>> area, int cost, int x, int y) {
//        if (x < 0 || y < 0 || x >= area.size() || y >= area.get(x).size()) {
//            return -1;
//        }
//        if (area.get(x).get(y) == 9) {
//            return cost;
//        }
//        if (this.visited[x][y] || area.get(x).get(y) == 0) {
//            return -1;
//        }
//        if (this.visited[x][y]) {
////            return cost + this.costsCache[x][y];
//        }
//        int minCost = IntStream.of(
//                recursiveSearch(area, cost + 1, x - 1, y),
//                recursiveSearch(area, cost + 1, x + 1, y),
//                recursiveSearch(area, cost + 1, x, y - 1),
//                recursiveSearch(area, cost + 1, x, y + 1)
//        ).filter(c -> c > 0).min().orElse(-1);
////        costsCache[x][y] = minCost;
//        return minCost;
//    }

}
