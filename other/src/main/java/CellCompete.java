/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CellCompete {

    public static void main(String[] args) {
     new CellCompete().cellCompete(new int[]{1, 0, 1}, 1);
    }

    public List<Integer> cellCompete(int[] states, int days)
    {
        int[] previousDay;
        for (int i = 0; i < days; i++) {
            previousDay = Arrays.copyOf(states, states.length);
            processArray(states, previousDay);
        }
        return Arrays.stream(states).boxed().collect(Collectors.toList());
    }

    private void processArray(final int[] result, final int[] doNotChange) {
        result[0] = doNotChange[1] == 0 ? 0 : 1;
        result[result.length - 1] = doNotChange[result.length - 2] == 0 ? 0 : 1;
        for (int i = 1; i < result.length - 1; i ++) {
            result[i] = doNotChange[i-1] == doNotChange[i+1] ? 0 : 1;
        }
    }
}
