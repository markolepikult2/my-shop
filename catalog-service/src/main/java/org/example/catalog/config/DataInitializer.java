package org.example.catalog.config;

import org.example.catalog.model.Product;
import org.example.catalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.stream.IntStream;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                IntStream.rangeClosed(1, 50).forEach(i -> {
                    Product p = new Product(
                            "Product " + i,
                            "Sample product description for item " + i,
                            new BigDecimal("" + (i * 3 + 0.99))
                    );
                    repository.save(p);
                });
            }
        };
    }
}
