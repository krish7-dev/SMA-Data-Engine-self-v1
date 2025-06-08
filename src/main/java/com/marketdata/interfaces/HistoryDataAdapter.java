package com.marketdata.interfaces;

import com.marketdata.model.Candle;

import java.util.Date;
import java.util.List;

public interface HistoryDataAdapter {
    List<Candle> fetchHistorical(String symbol, Date from, Date to) throws Exception;
}
