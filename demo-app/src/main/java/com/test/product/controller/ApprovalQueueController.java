package com.test.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.product.entity.ApprovalQueue;
import com.test.product.entity.Product;
import com.test.product.entity.ProductStatus;
import com.test.product.exception.ResourceNotFoundException;
import com.test.product.repository.ApprovalQueueRepository;

@RestController
@RequestMapping("/api/products/approval-queue")
public class ApprovalQueueController {
    @Autowired
    private ApprovalQueueRepository approvalQueueRepository;
    
    // API to Get Products in Approval Queue
    @GetMapping
    public List<ApprovalQueue> getProductsInApprovalQueue() {
        return approvalQueueRepository.findAllByOrderByRequestDateAsc();
    }
    
    // API to Approve a Product
    @PutMapping("/{approvalId}/approve")
    public ResponseEntity<?> approveProduct(@PathVariable Long approvalId) {
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval entry not found with id: " + approvalId));
        
        Product product = approvalQueue.getProduct();
        product.setStatus(ProductStatus.ACTIVE);
        approvalQueueRepository.delete(approvalQueue);
        return ResponseEntity.noContent().build();
    }
    
    // API to Reject a Product
    @PutMapping("/{approvalId}/reject")
    public ResponseEntity<?> rejectProduct(@PathVariable Long approvalId) {
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval entry not found with id: " + approvalId));
        
        approvalQueueRepository.delete(approvalQueue);
        return ResponseEntity.noContent().build();
    }
}

