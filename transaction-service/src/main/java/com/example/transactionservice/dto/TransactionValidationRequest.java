package com.example.transactionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionValidationRequest {

    @NotBlank(message = "transactionId is required")
    private String transactionId;

    @NotNull(message = "customerId is required")
    private Long customerId;

}
