package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.Trade;
import com.example.demo.model.TradeState;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TradeMongoDao extends MongoRepository<Trade, ObjectId> {

	public List<Trade> findByState(TradeState state);
}
