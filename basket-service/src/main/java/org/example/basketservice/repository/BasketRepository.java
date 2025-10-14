package org.example.basketservice.repository;

import org.example.basketservice.model.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends MongoRepository<Basket, String> {
}
