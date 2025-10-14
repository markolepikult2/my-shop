package org.example.basketservice.service;

import org.example.basketservice.model.Basket;
import org.example.basketservice.model.Item;
import org.example.basketservice.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public List<Item> getBasketItems(String userId) {
        return basketRepository.findById(userId)
                .map(Basket::getItems)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Item> addItemToBasket(String userId, Item newItem) {
        Basket basket = basketRepository.findById(userId)
                .orElse(new Basket(userId));

        Optional<Item> existingItemOpt = basket.getItems().stream()
                .filter(i -> i.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            // Item exists, update quantity
            Item existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
        } else {
            // Item does not exist, add it to the basket
            basket.getItems().add(newItem);
        }

        Basket updatedBasket = basketRepository.save(basket);
        return updatedBasket.getItems();
    }
}
