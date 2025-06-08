package com.marketdata.model;

import java.util.Date;

public class Candle {
    private String symbol;
    private Date timestamp;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;

    public String getSymbol() { return symbol; }
    public Date getTimestampDate() { return timestamp; }
    public long getTimestamp() { return timestamp.getTime(); }
    public double getOpen() { return open; }
    public double getHigh() { return high; }
    public double getLow() { return low; }
    public double getClose() { return close; }
    public double getVolume() { return volume; }
}
