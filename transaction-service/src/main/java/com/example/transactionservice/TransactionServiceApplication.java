package com.example.transactionservice;

import com.example.transactionservice.service.TransactionValidationServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the MOCK Transaction Service.
 *
 * This service is a stand-in for the real Transaction Service and exists
 * solely so the Dispute Service can be exercised in local/integration tests
 * without a live upstream dependency. It intentionally has no persistence
 * layer, no auth, and no business logic beyond simple, deterministic
 * validation rules - see {@link TransactionValidationServiceImp}.
 */
@SpringBootApplication
public class TransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }
}
