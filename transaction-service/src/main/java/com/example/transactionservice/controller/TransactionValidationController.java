package com.example.transactionservice.controller;

import com.example.transactionservice.dto.TransactionIsCreated;
import com.example.transactionservice.dto.TransactionCreationRequest;
import com.example.transactionservice.dto.TransactionValidationRequest;
import com.example.transactionservice.dto.TransactionValidationResponse;
import com.example.transactionservice.service.TransactionValidationServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The ONLY endpoint this mock exposes. Mirrors the shape of what the real
 * Transaction Service's validate-for-dispute endpoint is expected to return,
 * so the Dispute Service can be written against this contract now and pointed
 * at the real service later with no code changes on the Dispute Service side.
 */
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionValidationController {


    private final TransactionValidationServiceImp transactionValidationService;

    // Create a transaction
    @PostMapping
    public ResponseEntity<TransactionIsCreated> createTransaction(
            @Valid @RequestBody TransactionCreationRequest request) {

        TransactionIsCreated response =
                transactionValidationService.createTransaction(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Validate transaction for Dispute Service
    @PostMapping("/validate-for-dispute")
    public ResponseEntity<TransactionValidationResponse> validateForDispute(
            @Valid @RequestBody TransactionValidationRequest request) {

        TransactionValidationResponse response =
                transactionValidationService.validate(request);

        return ResponseEntity.ok(response);
    }
}
