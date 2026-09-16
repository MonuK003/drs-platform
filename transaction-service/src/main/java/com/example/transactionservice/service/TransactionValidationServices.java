package com.example.transactionservice.service;

import com.example.transactionservice.dto.TransactionIsCreated;
import com.example.transactionservice.dto.TransactionCreationRequest;
import com.example.transactionservice.dto.TransactionValidationRequest;
import com.example.transactionservice.dto.TransactionValidationResponse;

public interface TransactionValidationServices {

    public TransactionIsCreated createTransaction(TransactionCreationRequest request);
    public TransactionValidationResponse validate(TransactionValidationRequest request);

}
