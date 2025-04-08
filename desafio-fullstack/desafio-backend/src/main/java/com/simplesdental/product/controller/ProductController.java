package com.simplesdental.product.controller;

import com.simplesdental.product.model.Product;
import com.simplesdental.product.service.ProductService;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ------------------------------
    // Endpoints Legado (v1)
    // URL base: /api/products
    // Retorna o campo code convertido para o formato legado "PROD-" + code
    // ------------------------------

    @GetMapping("/api/products")
    @Transactional
    public List<Map<String, Object>> getAllProductsLegacy() {
        List<Product> products = productService.findAll();
        products.forEach(product -> {
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
        });
        return products.stream().map(product -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", product.getId());
            map.put("name", product.getName());
            map.put("description", product.getDescription());
            map.put("price", product.getPrice());
            map.put("status", product.getStatus());
            map.put("code", product.getCode() != null ? "PROD-" + product.getCode() : null);
            map.put("category", product.getCategory());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<Map<String, Object>> getProductByIdLegacy(@PathVariable Long id) {
        Optional<Product> optProduct = productService.findById(id);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", product.getId());
            map.put("name", product.getName());
            map.put("description", product.getDescription());
            map.put("price", product.getPrice());
            map.put("status", product.getStatus());
            map.put("code", product.getCode() != null ? "PROD-" + product.getCode() : null);
            map.put("category", product.getCategory());
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createProductLegacy(@Valid @RequestBody Product product) {
        Product savedProduct = productService.save(product);
        Map<String, Object> map = new HashMap<>();
        map.put("id", savedProduct.getId());
        map.put("name", savedProduct.getName());
        map.put("description", savedProduct.getDescription());
        map.put("price", savedProduct.getPrice());
        map.put("status", savedProduct.getStatus());
        map.put("code", savedProduct.getCode() != null ? "PROD-" + savedProduct.getCode() : null);
        map.put("category", savedProduct.getCategory());
        return map;
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<Map<String, Object>> updateProductLegacy(@PathVariable Long id, @Valid @RequestBody Product product) {
        Optional<Product> optExisting = productService.findById(id);
        if (optExisting.isPresent()) {
            product.setId(id);
            Product updated = productService.save(product);
            Map<String, Object> map = new HashMap<>();
            map.put("id", updated.getId());
            map.put("name", updated.getName());
            map.put("description", updated.getDescription());
            map.put("price", updated.getPrice());
            map.put("status", updated.getStatus());
            map.put("code", updated.getCode() != null ? "PROD-" + updated.getCode() : null);
            map.put("category", updated.getCategory());
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Void> deleteProductLegacy(@PathVariable Long id) {
        Optional<Product> optProduct = productService.findById(id);
        if (optProduct.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ------------------------------
    // Endpoints Nova Versão (v2)
    // URL base: /api/v2/products
    // Retorna os dados normalmente, com o campo code como Integer
    // ------------------------------

    @GetMapping("/api/v2/products")
    @Transactional
    public List<Product> getAllProductsV2() {
        List<Product> products = productService.findAll();
        products.forEach(product -> {
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
        });
        return products;
    }

    @GetMapping("/api/v2/products/{id}")
    public ResponseEntity<Product> getProductByIdV2(@PathVariable Long id) {
        Optional<Product> optProduct = productService.findById(id);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/v2/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProductV2(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/api/v2/products/{id}")
    public ResponseEntity<Product> updateProductV2(@PathVariable Long id, @Valid @RequestBody Product product) {
        Optional<Product> optExisting = productService.findById(id);
        if (optExisting.isPresent()) {
            product.setId(id);
            Product updated = productService.save(product);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/v2/products/{id}")
    public ResponseEntity<Void> deleteProductV2(@PathVariable Long id) {
        Optional<Product> optProduct = productService.findById(id);
        if (optProduct.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}