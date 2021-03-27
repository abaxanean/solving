/*
 * Copyright 2021 Ivanti, Inc.
 * All rights reserved.
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;

public class TraderTest {

    final Trader trader = new Trader();

    @Test
    public void test() {
        final Map<String, Double> start = new HashMap<>();
        start.put("A", 12.01);
        start.put("B", 12.01);
        start.put("C", 12.01);
        final Map<String, Double> end = new HashMap<>();
        end.put("A", 12.1);
        end.put("B", 12.2);
        end.put("C", 12.3);
        final String[] best = this.trader.findBestPerforming(start, end);
        System.out.println(Arrays.toString(best));
    }

}
