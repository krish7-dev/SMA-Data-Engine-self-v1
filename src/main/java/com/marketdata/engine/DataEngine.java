package com.marketdata.engine;

import com.marketdata.model.Candle;
import com.marketdata.plugin.Upstox;

import java.util.Date;
import java.util.List;

public class DataEngine {

    public void fetchHistoryData() {
        try {
            Date from = new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L); // 3 days ago
            Date to = new Date(); // now

            List<Candle> historyData = new Upstox().fetchHistorical(
                    "NSE_EQ|INE002A01018",
                    to,
                    from
            );

            for (Candle candle : historyData) {
                System.out.println(candle.getClose());
            }

        } catch (Exception e) {
            e.printStackTrace(); // Always log errors during development
        }
    }
}
