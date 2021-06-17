package com.blaze.server.repositories.mongo;

import com.blaze.server.models.Order;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String>{

}