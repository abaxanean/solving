/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha;

import com.google.common.base.MoreObjects;

public class Statistic {

    int beatingSpy;
    int loosingSpy;
    int total;
    double totalGain;
    double totalSpyGain;

    public void reportStock(final double stockGain, final double spyGain) {
        if (stockGain > spyGain) {
            this.beatingSpy++;
        } else {
            this.loosingSpy++;
        }
        totalGain += stockGain;
        totalSpyGain += spyGain;
        total++;
    }

    public Report generateReport() {
        return new Report(this.total, this.beatingSpy, this.loosingSpy, this.totalGain, this.totalSpyGain);
    }

    static class Report {

        final int total;
        final double beatingSpy;
        final double loosingSpy;
        double gainVersusSpy;

        public Report(final int total, final int beatingSpy, final int loosingSpy, final double totalGain, final double totalSpyGain) {
            final double totalDouble = 1.0d * total;
            this.total = total;
            this.beatingSpy = (beatingSpy / totalDouble) * 100;
            this.loosingSpy = (loosingSpy / totalDouble) * 100;
            this.gainVersusSpy = (totalGain / totalSpyGain) * 100 - 100;
        }

        @Override
        public String toString() {
            return String.format("%d trades. %5.2f beating SPY. %5.2f loosing to SPY. Average gain versus SPY %6.2f", total, beatingSpy, loosingSpy, gainVersusSpy);
        }
    }

}
