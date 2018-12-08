/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MinimumDistance {

    public static void main(String[] args) {
        System.out.println(new MinimumDistance().minimumDistance(3, 3, Arrays.asList(
                Arrays.asList(1, 0, 9),
                Arrays.asList(1, 1, 1),
                Arrays.asList(1, 1, 1)
        )));
    }

    int[][] costsCache;

    int minimumDistance(int numRows, int numColumns, List<List<Integer>> area) {
        costsCache = new int[numRows][numColumns];
        return recursiveSearch(area, 0, 0, 0);
    }

    int recursiveSearch(List<List<Integer>> area, int cost, int x, int y) {
        if (x < 0 || y < 0 || x >= area.size() || y >= area.get(x).size()) {
            return -1;
        }
        if (area.get(x).get(y) == 9) {
            return cost;
        }
        if (this.costsCache[x][y] == -1 || area.get(x).get(y) == 0) {
            return -1;
        }
        if (this.costsCache[x][y] > 0) {
            return cost + this.costsCache[x][y];
        }
        int minCost = IntStream.of(
                recursiveSearch(area, cost + 1, x - 1, y),
                recursiveSearch(area, cost + 1, x + 1, y),
                recursiveSearch(area, cost + 1, x, y - 1),
                recursiveSearch(area, cost + 1, x, y + 1)
        ).filter(c -> c > 0).min().orElse(-1);
        costsCache[x][y] = minCost;
        return minCost;
    }

}
