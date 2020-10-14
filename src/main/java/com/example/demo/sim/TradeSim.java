package com.example.demo.sim;

import java.util.List;

import com.example.demo.dao.TradeMongoDao;
import com.example.demo.model.Trade;
import com.example.demo.model.TradeState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TradeSim {
    private static final Logger LOG = LoggerFactory.getLogger(TradeSim.class);

    @Autowired
    private TradeMongoDao tradeDao;

    @Transactional
    public List<Trade> findTradesForProcessing(){
        List<Trade> foundTrades = tradeDao.findByState(TradeState.CREATED);

        for(Trade thisTrade: foundTrades) {
            thisTrade.setState(TradeState.PROCESSING);
            tradeDao.save(thisTrade);
        }

        return foundTrades;
    }

    @Transactional
    public List<Trade> findTradesForFilling(){
        List<Trade> foundTrades = tradeDao.findByState(TradeState.PROCESSING);

        for(Trade thisTrade: foundTrades) {
            if((int) (Math.random()*10) > 8) {
                thisTrade.setState(TradeState.REJECTED);
            }
            else {
                thisTrade.setState(TradeState.FILLED);
            }
            tradeDao.save(thisTrade);
        }

        return foundTrades;
    }

    @Scheduled(fixedRateString = "${scheduleRateMs:10000}")
    public void runSim() {
        LOG.debug("Main loop running!");

        int tradesForFilling = findTradesForFilling().size();
        LOG.debug("Found " + tradesForFilling + " trades to be filled/rejected");

        int tradesForProcessing = findTradesForProcessing().size();
        LOG.debug("Found " + tradesForProcessing + " trades to be processed");

    }
}
