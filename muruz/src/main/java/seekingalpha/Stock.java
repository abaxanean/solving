/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha;

import java.time.LocalDate;

public class Stock {

    private String stock;
    private LocalDate date;
    private LocalDate sellDate;
    private double sellPrice;
    private double price;
    private double spyPrice;

    public Stock() {
    }

    public String getStock() {
        return this.stock;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public double getPrice() {
        return this.price;
    }

    public void setStock(final String stock) {
        this.stock = stock.toUpperCase();
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public double getSpyPrice() {
        return this.spyPrice;
    }

    public void setSpyPrice(final double spyPrice) {
        this.spyPrice = spyPrice;
    }

    public LocalDate getSellDate() {
        return this.sellDate;
    }

    public void setSellDate(final LocalDate sellDate) {
        this.sellDate = sellDate;
    }

    public double getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(final double sellPrice) {
        this.sellPrice = sellPrice;
    }
}
