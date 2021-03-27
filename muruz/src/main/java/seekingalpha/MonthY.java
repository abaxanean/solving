/*
 * Copyright 2021 Ivanti, Inc.
 * All rights reserved.
 */

package seekingalpha;

import java.time.Month;
import java.util.Objects;

public class MonthY implements Comparable<MonthY> {
    final int year;
    final Month month;

    public MonthY(final int year, final Month month) {
        this.month = month;
        this.year = year;
    }

    @Override
    public int compareTo(final MonthY other) {
        if (this.year == other.year) {
            return this.month.compareTo(other.month);
        }
        return this.year - other.year;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MonthY monthY = (MonthY)o;
        return year == monthY.year && month == monthY.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }

    @Override
    public String toString() {
        return this.year + " " + this.month;
    }
}
