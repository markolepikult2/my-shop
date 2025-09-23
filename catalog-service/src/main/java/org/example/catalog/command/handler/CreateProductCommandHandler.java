package org.example.catalog.command.handler;

import org.example.catalog.command.dto.CreateProductCommand;
import org.example.catalog.model.Product;
import org.example.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductCommandHandler {
    private final ProductRepository productRepository;

    public CreateProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long handle(CreateProductCommand command) {
        Product product = new Product(command.getName(), command.getDescription(), command.getPrice());
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }
}
