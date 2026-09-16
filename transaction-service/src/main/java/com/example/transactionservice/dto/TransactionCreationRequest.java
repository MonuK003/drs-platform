package com.example.transactionservice.dto;

import com.example.transactionservice.Enum.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What the Dispute Service sends when it needs to confirm a transaction
 * exists and is eligible to be disputed before creating/updating a dispute case.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreationRequest {

    @NotBlank(message = "transactionId is required")
    private String transactionId;

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotNull
    private Status status;

}