package com.marketdata;

import com.marketdata.engine.DataEngine;
import com.marketdata.engine.ReplayEngine;
import com.marketdata.model.Candle;
import com.marketdata.ws.MarketDataWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main{

    @Autowired
    private MarketDataWebSocketHandler socketHandler;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
