package com.marketdata.api;

import com.marketdata.engine.DataEngine;
import com.marketdata.engine.ReplayEngine;
import com.marketdata.model.Candle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private DataEngine dataEngine;

    @Autowired
    private ReplayEngine replayEngine;

    @GetMapping("/history")
    public List<Candle> getHistory(
            @RequestParam String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    )
    {
        return dataEngine.fetchHistoryData(symbol, from, to);
    }

    @GetMapping("/replay/start")
    public String startReplay(
            @RequestParam String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    )
    {
        List<Candle> candles = dataEngine.fetchHistoryData(symbol, from, to);
        replayEngine.loadCandles(candles);
        replayEngine.startReplay();
        return "✅ Replay started with \" + candles.size() + \" candles.";
    }

    @GetMapping("/replay/stop")
    public String stopReplay() {
        replayEngine.stopReplay();
        return "⏹️ Replay stopped.";
    }
}
