package org.example.catalog.query.handler;

import org.example.catalog.model.Product;
import org.example.catalog.query.GetProductsByIdsQuery;
import org.example.catalog.query.dto.ProductDto;
import org.example.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetProductsByIdsQueryHandler {

    private final ProductRepository productRepository;

    public GetProductsByIdsQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> handle(GetProductsByIdsQuery query) {
        List<Product> products = productRepository.findAllById(query.getProductIds());
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
