/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seekingalpha.Stock;

public interface QuotesProvider {

    Set<LocalDate> getAllDates();

    Map<String, Double> getPrices(LocalDate date);

}
