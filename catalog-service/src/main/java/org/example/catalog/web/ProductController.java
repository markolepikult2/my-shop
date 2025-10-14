package org.example.catalog.web;

import org.example.catalog.command.dto.CreateProductCommand;
import org.example.catalog.command.handler.CreateProductCommandHandler;
import org.example.catalog.query.GetAllProductsQuery;
import org.example.catalog.query.GetProductsByIdsQuery;
import org.example.catalog.query.dto.ProductDto;
import org.example.catalog.query.handler.GetAllProductsQueryHandler;
import org.example.catalog.query.handler.GetProductsByIdsQueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final CreateProductCommandHandler createProductHandler;
    private final GetAllProductsQueryHandler getAllProductsHandler;
    private final GetProductsByIdsQueryHandler getProductsByIdsHandler;

    public ProductController(CreateProductCommandHandler createProductHandler,
                             GetAllProductsQueryHandler getAllProductsHandler,
                             GetProductsByIdsQueryHandler getProductsByIdsHandler) {
        this.createProductHandler = createProductHandler;
        this.getAllProductsHandler = getAllProductsHandler;
        this.getProductsByIdsHandler = getProductsByIdsHandler;
    }

    @PostMapping("/admin/product")
    public ResponseEntity<Long> addProduct(@RequestBody CreateProductCommand command) {
        Long productId = createProductHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @GetMapping("/products")
    public Page<ProductDto> getAll(Pageable pageable) {
        GetAllProductsQuery query = new GetAllProductsQuery(pageable);
        return getAllProductsHandler.handle(query);
    }

    @GetMapping("/products/list")
    public List<ProductDto> getProductsByIds(@RequestParam("ids") List<Long> ids) {
        GetProductsByIdsQuery query = new GetProductsByIdsQuery(ids);
        return getProductsByIdsHandler.handle(query);
    }
}
