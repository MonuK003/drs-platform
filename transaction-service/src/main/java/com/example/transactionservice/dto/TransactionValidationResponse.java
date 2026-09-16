package com.example.transactionservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * What this mock service hands back to the Dispute Service.
 *
 * `valid` is the field the Dispute Service should actually branch on.
 * The rest is context useful for logging/debugging and for building
 * realistic-looking dispute records in local testing.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionValidationResponse {

    private String transactionId;

    private boolean valid;

    private Long customerId;

    private BigDecimal amount;

    private String status;

    private String message;
}