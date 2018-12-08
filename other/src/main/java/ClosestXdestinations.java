/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ClosestXdestinations {

    public static void main(String[] args) {
        new ClosestXdestinations().ClosestXdestinations(6, Arrays.asList(Arrays.asList(3, 6), Arrays.asList(2, 4), Arrays.asList(5, 3),
                Arrays.asList(2, 7), Arrays.asList(1, 8), Arrays.asList(7, 9)), 3);
    }

    List<List<Integer>> ClosestXdestinations(int numDestinations,
                                             List<List<Integer>> allLocations,
                                             int numDeliveries) {
        TreeMap<Double, List<Integer>> sortedDistance = new TreeMap<>();
        for (List<Integer> list : allLocations) {
            sortedDistance.put(distance(list), list);
        }
        List<List<Integer>> result = new ArrayList<>(numDeliveries);
        Iterator<Map.Entry<Double, List<Integer>>> it = sortedDistance.entrySet().iterator();
        for (int i = 0; i < numDeliveries && it.hasNext(); i++) {
            result.add(it.next().getValue());
        }
        return result;
    }

    private double distance(List<Integer> coords) {
        int x = coords.get(0);
        int y = coords.get(1);
        return Math.sqrt(x * x + y * y);
    }
}
