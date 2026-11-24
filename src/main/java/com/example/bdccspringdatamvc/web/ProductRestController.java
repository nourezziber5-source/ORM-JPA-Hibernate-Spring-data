package com.example.bdccspringdatamvc.web;




import com.example.bdccspringdatamvc.entities.Product;
import com.example.bdccspringdatamvc.repository.ProductRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search products by keyword
    @GetMapping("/find/products")
    public List<Product> searchProducts(@RequestParam String kw) {
        return productRepository.findByNameContains(kw);
    }

    // Add a product
    @PostMapping("/products")
    public Product saveProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Update a product
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {

        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setQuantity(updatedProduct.getQuantity());

                    return ResponseEntity.ok(
                            productRepository.save(product)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
