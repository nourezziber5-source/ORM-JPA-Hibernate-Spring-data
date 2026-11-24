package com.example.bdccspringdatamvc.repository;

import com.example.bdccspringdatamvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Search products by name
    java.util.List<Product> findByNameContains(String kw);

}
