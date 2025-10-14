package org.example.basketservice.controller;

import org.example.basketservice.model.Item;
import org.example.basketservice.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getBasketItems(@RequestHeader("X-User-Id") String userId) {
        List<Item> items = basketService.getBasketItems(userId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/items")
    public ResponseEntity<List<Item>> addItemToBasket(@RequestHeader("X-User-Id") String userId, @RequestBody Item item) {
        List<Item> updatedItems = basketService.addItemToBasket(userId, item);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedItems);
    }
}
