package com.marketdata.engine;

import com.marketdata.model.Candle;
import com.marketdata.plugin.Upstox;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DataEngine {

    private List<Candle> historyData;

    public List<Candle> fetchHistoryData(String symbol, Date from, Date to) {
        try {
//            Date from = new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L); // 3 days ago
//            Date to = new Date(); // now

            historyData = new Upstox().fetchHistorical(
                    symbol,
                    to,
                    from
            );

//            for (Candle candle : historyData) {
//                System.out.println(candle.getClose());
//            }

        } catch (Exception e) {
            e.printStackTrace(); // Always log errors during development
        }
        return historyData;
    }

}
