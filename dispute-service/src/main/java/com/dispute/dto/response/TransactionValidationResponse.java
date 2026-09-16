package com.dispute.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionValidationResponse {
    private String transactionId;

    private boolean valid;

    private Long customerId;

    private BigDecimal amount;

    private String status;

    private String message;

}
