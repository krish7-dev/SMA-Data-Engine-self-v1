package com.marketdata.config;

import com.marketdata.ws.MarketDataWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(marketDataWebSocketHandler(), "/ws/marketdata")
                .setAllowedOrigins("*");
    }

    @Bean
    public MarketDataWebSocketHandler marketDataWebSocketHandler() {
        return new MarketDataWebSocketHandler();
    }
}
