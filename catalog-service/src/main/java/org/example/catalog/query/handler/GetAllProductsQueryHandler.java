package org.example.catalog.query.handler;

import org.example.catalog.model.Product;
import org.example.catalog.query.GetAllProductsQuery;
import org.example.catalog.query.dto.ProductDto;
import org.example.catalog.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetAllProductsQueryHandler {
    private final ProductRepository productRepository;

    public GetAllProductsQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductDto> handle(GetAllProductsQuery query) {
        Page<Product> productPage = productRepository.findAll(query.getPageable());
        return productPage.map(this::convertToDto);
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
