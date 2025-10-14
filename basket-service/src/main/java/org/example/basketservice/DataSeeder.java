package org.example.basketservice;

import org.example.basketservice.model.Basket;
import org.example.basketservice.model.Item;
import org.example.basketservice.repository.BasketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev") // Only run this seeder in the 'dev' profile
public class DataSeeder implements CommandLineRunner {

    private final BasketRepository basketRepository;

    public DataSeeder(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String testUserId = "test-user";

        // Check if a basket for the test user already exists
        if (basketRepository.findById(testUserId).isEmpty()) {
            System.out.println("No basket found for user 'test-user'. Seeding data...");

            Basket testBasket = new Basket(testUserId);
            testBasket.setItems(List.of(
                new Item(1, 2), // 2 of product with ID 1
                new Item(3, 1)  // 1 of product with ID 3
            ));

            basketRepository.save(testBasket);
            System.out.println("Sample basket for 'test-user' created.");
        }
    }
}
