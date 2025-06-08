package com.marketdata.engine;

import com.marketdata.model.Candle;
import com.marketdata.ws.MarketDataWebSocketHandler;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReplayEngine {

    private final MarketDataWebSocketHandler socketHandler;
    private List<Candle> candles;

    public ReplayEngine(MarketDataWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void loadCandles(List<Candle> candles) {
        this.candles = candles;
    }

    public void startReplay() {
        new Thread(() -> {
            try {
                for (Candle c : candles) {
                    String json = candleToJson(c); // manual JSON
                    socketHandler.broadcast(json);
                    System.out.println(json);
                    Thread.sleep(100); // simulate market flow
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String candleToJson(Candle c) {
        return String.format(
                "{" +
                        "\"symbol\":\"%s\"," +
                        "\"timestamp\":\"%s\"," +
                        "\"open\":%.2f," +
                        "\"high\":%.2f," +
                        "\"low\":%.2f," +
                        "\"close\":%.2f," +
                        "\"volume\":%.0f" +
                        "}",
                c.getSymbol(),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(c.getTimestamp()),
                c.getOpen(),
                c.getHigh(),
                c.getLow(),
                c.getClose(),
                c.getVolume()
        );
    }
}
