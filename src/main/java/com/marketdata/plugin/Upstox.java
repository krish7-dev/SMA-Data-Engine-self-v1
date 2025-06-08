package com.marketdata.plugin;

import com.marketdata.interfaces.HistoryDataAdapter;
import com.marketdata.model.Candle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Upstox implements HistoryDataAdapter {

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public List<Candle> fetchHistorical(String symbol, Date from, Date to) throws IOException, InterruptedException {

        String encodedInstrumentKey = symbol.replace(" ", "%20").replace("|", "%7C");
        String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(from);
        String toDate = new SimpleDateFormat("yyyy-MM-dd").format(to);
        System.out.println(fromDate);
        System.out.println(toDate);

        String url = String.format(
                "https://api.upstox.com/v3/historical-candle/%s/minutes/1/%s/%s",
                encodedInstrumentKey, fromDate, toDate
        );

        System.out.println("📡 URL: " + url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);
        // Find the start of the candles array
        int start = body.indexOf("[[");
        int end = body.indexOf("]]") + 2;

        if (start == -1 || end == -1) return List.of(); // no data found

        String candlesArray = body.substring(start, end);

        // Split into rows of candles
        String[] rows = candlesArray.split("\\],\\[");

        List<Candle> candles = new ArrayList<>();
        for (String row : rows) {
            // Clean up each row
            row = row.replace("[", "").replace("]", "").replace("\"", "");
            String[] parts = row.split(",");

            if (parts.length < 6) continue;

            OffsetDateTime odt = OffsetDateTime.parse(parts[0], DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            Date timestamp = Date.from(odt.toInstant());

            double open = Double.parseDouble(parts[1]);
            double high = Double.parseDouble(parts[2]);
            double low = Double.parseDouble(parts[3]);
            double close = Double.parseDouble(parts[4]);
            double volume = Double.parseDouble(parts[5]);

            candles.add(new Candle(symbol, timestamp, open, high, low, close, volume));
        }

        return candles;
    }
}
