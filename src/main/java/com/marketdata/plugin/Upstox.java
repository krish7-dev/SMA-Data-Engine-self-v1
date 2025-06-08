package com.marketdata.plugin;

import com.marketdata.interfaces.HistoryDataAdapter;
import com.marketdata.model.Candle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Upstox implements HistoryDataAdapter {

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public List<Candle> fetchHistorical(String symbol, Date from, Date to) throws IOException, InterruptedException{

        String instrumentKey = "NSE_EQ|INE002A01018";
        String encodedInstrumentKey = symbol.replace(" ", "%20").replace("|", "%7C");

        String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(from);
        String toDate = new SimpleDateFormat("yyyy-MM-dd").format(to);

        String url = String.format(
                "https://api.upstox.com/v3/historical-candle/%s/minutes/1/%s/%s",
                encodedInstrumentKey,
                fromDate,
                toDate
        );

        System.out.println("📡 URL: " + url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept","application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
        return Arrays.asList(new Candle());
    }
}
