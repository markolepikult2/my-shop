package org.example.catalog.web;

import org.example.catalog.model.Product;
import org.example.catalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getAll(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @PostMapping("/admin/product")
    public Product addProduct(@RequestBody Product product) {
        return productService.save(product);
    }
}
