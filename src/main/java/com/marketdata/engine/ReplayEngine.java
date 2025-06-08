package com.marketdata.engine;

import com.marketdata.model.Candle;
import com.marketdata.ws.MarketDataWebSocketHandler;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ReplayEngine {

    private final MarketDataWebSocketHandler socketHandler;
    private List<Candle> candles;

    private volatile boolean running = false;
    private Thread replayThread;

    public ReplayEngine(MarketDataWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void loadCandles(List<Candle> candles) {
        this.candles = candles;
    }

    public synchronized void startReplay() {
        if (running) {
            System.out.println("⏩ Replay already running.");
            return;
        }

        running = true;
        replayThread = new Thread(() -> {
            try {
                for (Candle c : candles) {
                    if (!running) break;

                    String json = candleToJson(c);
                    socketHandler.broadcast(json);
                    System.out.println(json);
                    Thread.sleep(100); // simulate market flow
                }
            } catch (InterruptedException e) {
                System.out.println("⛔ Replay thread interrupted.");
            } finally {
                running = false;
            }
        });

        replayThread.start();
    }

    public synchronized void stopReplay() {
        if (running && replayThread != null) {
            running = false;
            replayThread.interrupt();
            System.out.println("⏹️ Replay stopped.");
        } else {
            System.out.println("⚠️ No replay is running.");
        }
    }

    public boolean isRunning() {
        return running;
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
