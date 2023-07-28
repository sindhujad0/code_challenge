package com.test.product.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.product.entity.Product;
import com.test.product.entity.ProductStatus;
import com.test.product.exception.ResourceNotFoundException;
import com.test.product.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listActiveProducts() {
        return productRepository.findByStatusOrderByPostedDateDesc(ProductStatus.ACTIVE);
    }

    public List<Product> searchProducts(
            String productName, BigDecimal minPrice, BigDecimal maxPrice,
            LocalDateTime minPostedDate, LocalDateTime maxPostedDate
    ) {
        if (productName == null) {
            productName = "";
        }

        if (minPrice == null) {
            minPrice = BigDecimal.ZERO;
        }

        if (maxPrice == null) {
            maxPrice = BigDecimal.valueOf(10000);
        }

        if (minPostedDate == null) {
            minPostedDate = LocalDateTime.MIN;
        }

        if (maxPostedDate == null) {
            maxPostedDate = LocalDateTime.now();
        }

        return productRepository.findByNameContainingAndPriceBetweenAndPostedDateBetween(
                productName, minPrice, maxPrice, minPostedDate, maxPostedDate
        );
    }

    public Product createProduct(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(10000)) > 0) {
            product.setStatus(ProductStatus.INACTIVE);
        } else if (product.getPrice().compareTo(BigDecimal.valueOf(5000)) > 0) {
            product.setStatus(ProductStatus.IN_APPROVAL_QUEUE);
        } else {
            product.setStatus(ProductStatus.ACTIVE);
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        BigDecimal previousPrice = existingProduct.getPrice();
        BigDecimal newPrice = updatedProduct.getPrice();

        if (newPrice.compareTo(previousPrice.multiply(BigDecimal.valueOf(1.5))) > 0) {
            existingProduct.setStatus(ProductStatus.IN_APPROVAL_QUEUE);
        } else {
            existingProduct.setStatus(ProductStatus.ACTIVE);
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        product.setStatus(ProductStatus.IN_APPROVAL_QUEUE);
        productRepository.delete(product);
    }
}

