package com.marketdata;

import com.marketdata.engine.DataEngine;
import com.marketdata.model.Candle;
import com.marketdata.plugin.Upstox;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String args[]){
        new DataEngine().fetchHistoryData();
    }
}