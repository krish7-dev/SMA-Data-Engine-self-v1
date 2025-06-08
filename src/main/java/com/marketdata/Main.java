package com.marketdata;

import com.marketdata.model.Candle;
import com.marketdata.plugin.Upstox;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String args[]){
        try{
            List<Candle> historyData = new Upstox().fetchHistorical("NSE_EQ|INE002A01018",new Date(),new Date());
            System.out.println(historyData);
        }
        catch (Exception e){

        }
    }
}