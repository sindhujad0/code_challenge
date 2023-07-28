package com.test.product.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.product.entity.Product;
import com.test.product.entity.ProductStatus;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByPostedDateDesc(ProductStatus status);

    List<Product> findByNameContainingAndPriceBetweenAndPostedDateBetween(
            String productName, BigDecimal minPrice, BigDecimal maxPrice,
            LocalDateTime minPostedDate, LocalDateTime maxPostedDate
    );
}