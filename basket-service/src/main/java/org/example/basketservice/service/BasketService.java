package org.example.basketservice.service;

import org.example.basketservice.model.Item;

import java.util.List;

public interface BasketService {

    /**
     * Retrieves all items from the basket for a given user.
     * @param userId The ID of the user.
     * @return A list of items in the user's basket.
     */
    List<Item> getBasketItems(String userId);

    /**
     * Adds an item to the user's basket. If the item already exists,
     * its quantity is updated.
     * @param userId The ID of the user.
     * @param item The item to add.
     * @return The updated list of items in the basket.
     */
    List<Item> addItemToBasket(String userId, Item item);
}
